package lk.ijse.glowcare_backend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.ijse.glowcare_backend.dto.RoutineConflictRequestDTO;
import lk.ijse.glowcare_backend.dto.RoutineConflictResponseDTO;
import lk.ijse.glowcare_backend.entity.RoutineStep;
import lk.ijse.glowcare_backend.repository.RoutineStepRepository;
import lk.ijse.glowcare_backend.service.SmartEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmartEngineServiceImpl implements SmartEngineService {

    // Groq deprecated llama-3.3-70b-versatile on 2026-08-16.
    // Recommended replacements per Groq's deprecation notice: openai/gpt-oss-120b or qwen/qwen3.6-27b.
    // Keep this in sync with SkinAnalysisServiceImpl.GROQ_MODEL — consider extracting to a shared
    // @ConfigurationProperties bean so there's only one place to update next time Groq deprecates a model.
    private static final String GROQ_MODEL = "openai/gpt-oss-120b";

    private final RoutineStepRepository routineStepRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String GROQ_URL; // Using application.properties for URL

    @Override
    @SuppressWarnings("unchecked")
    public RoutineConflictResponseDTO checkConflict(RoutineConflictRequestDTO request) {

        // 1. Get existing products in this specific routine
        List<RoutineStep> existingSteps = routineStepRepository.findByPathCategoryAndZoneAndTimeOfDay(
                request.getPathCategory(), request.getZone(), request.getTimeOfDay()
        );

        List<String> currentProducts = existingSteps.stream()
                .map(RoutineStep::getProductName)
                .collect(Collectors.toList());

        // 2. If no products exist yet, there can't be a conflict
        if (currentProducts.isEmpty()) {
            return RoutineConflictResponseDTO.builder()
                    .hasConflict(false)
                    .reason("")
                    .original(request.getNewProduct())
                    .alternative("")
                    .build();
        }

        // 3. Build the AI System Prompt
        String systemPrompt = """
                You are a highly experienced, strict, and evidence-based dermatologist AI.
                
                Your task is to validate whether a NEW skincare product can be safely added to an EXISTING skincare routine.
                
                INPUT DATA:
                You will receive:
                1. newProduct: {
                    name: string,
                    ingredients: string[],
                    routineStep: string,  // e.g., cleanser, serum, moisturizer
                    timeOfUse: string     // "morning", "night", or "both"
                }
                2. existingRoutine: [
                    {
                      name: string,
                      ingredients: string[],
                      routineStep: string,
                      timeOfUse: string
                    }
                ]
                
                YOUR RESPONSIBILITIES:
                - Analyze ingredient-level interactions between the NEW product and ALL existing products.
                - Consider:
                  • Known dangerous or irritating combinations (e.g., Retinol + AHA/BHA, Benzoyl Peroxide + Retinol)
                  • Duplicate strong actives causing over-exfoliation
                  • Conflicts ONLY if they occur at the SAME time of use (morning vs night matters)
                - Ignore minor or harmless overlaps.
                
                DECISION RULES:
                - Set "hasConflict: true" ONLY if there is a clinically significant or well-known harmful interaction.
                - If products can coexist safely (even at different times), return "hasConflict: false".
                
                IF CONFLICT EXISTS:
                - Provide a short, medically accurate reason.
                - Suggest a safer alternative ingredient or product type (not a brand).
                
                IF NO CONFLICT:
                - Keep reason and alternative as empty strings.
                
                STRICT OUTPUT RULES:
                - Return ONLY valid JSON.
                - No markdown, no explanations, no extra text.
                
                OUTPUT FORMAT:
                {
                  "hasConflict": boolean,
                  "reason": string,
                  "alternative": string
                }
                
                IMPORTANT:
                - Be conservative and avoid false positives.
                - Do NOT guess unknown ingredients.
                - If data is insufficient, assume safe unless a known conflict exists.
                """;

        String userContext = String.format(
                "Attempting to add '%s' to the '%s' routine in the '%s'. Current products: %s.",
                request.getNewProduct(), request.getZone(), request.getTimeOfDay(), String.join(", ", currentProducts)
        );

        // 4. Construct Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        // 5. Construct Groq Payload using Maps (Safer serialization)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", GROQ_MODEL);
        requestBody.put("temperature", 0.1); // Low temp for factual consistency

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userContext)
        );
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 6. Call Groq and Parse Response
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, entity, Map.class);

            if (response.getBody() == null) {
                throw new RuntimeException("Received empty response from Groq API.");
            }

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new RuntimeException("Groq API returned no choices in the response.");
            }

            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            String rawContent = (String) message.get("content");

            if (rawContent == null || rawContent.isBlank()) {
                throw new RuntimeException("Groq API returned empty content.");
            }

            // Clean markdown if Groq ignores system instructions
            String cleanJson = rawContent.replace("```json", "").replace("```", "").trim();

            // Map clean JSON string back to our DTO
            RoutineConflictResponseDTO aiResult = objectMapper.readValue(cleanJson, RoutineConflictResponseDTO.class);
            aiResult.setOriginal(request.getNewProduct()); // Ensure original product is tracked

            return aiResult;

        } catch (HttpClientErrorException e) {
            // Surface Groq's actual error body (model_not_found, invalid_api_key, rate_limit, etc.)
            // so this is diagnosable from the logs instead of a generic message.
            log.error("Groq API rejected the conflict-check request ({}): {}", e.getStatusCode(), e.getResponseBodyAsString());
            return fallbackAllow(request, "AI Validation Offline - Allowed by default");
        } catch (Exception e) {
            log.error("Smart Engine AI Error: {}", e.getMessage(), e);
            // Fallback: If AI fails, allow the product to pass safely to not block the user.
            // NOTE: this means a real ingredient conflict will NOT be caught while Groq is failing.
            // Consider surfacing a warning banner to the user/admin when this fallback path is hit
            // repeatedly, since it silently disables the safety check rather than just degrading UX.
            return fallbackAllow(request, "AI Validation Offline - Allowed by default");
        }
    }

    private RoutineConflictResponseDTO fallbackAllow(RoutineConflictRequestDTO request, String reason) {
        return RoutineConflictResponseDTO.builder()
                .hasConflict(false)
                .reason(reason)
                .original(request.getNewProduct())
                .alternative("")
                .build();
    }
}