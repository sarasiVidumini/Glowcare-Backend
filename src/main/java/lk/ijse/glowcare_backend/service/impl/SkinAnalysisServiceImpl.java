package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.AnalysisRequestDTO;
import lk.ijse.glowcare_backend.entity.SkinQuestions;
import lk.ijse.glowcare_backend.repository.SkinQuestionsRepository;
import lk.ijse.glowcare_backend.service.SkinAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkinAnalysisServiceImpl implements SkinAnalysisService {

    private final SkinQuestionsRepository questionRepo;
    private final RestTemplate restTemplate;

    @Value("${groq.api.key}")
    private String groqApiKey;
    @Value("${groq.api.url}")
    private String GROQ_URL;


    @Override
    public List<SkinQuestions> getQuestionsByBodyPart(String bodyPart) {
        return questionRepo.findByBodyPartIgnoreCase(bodyPart);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String generateSkinAnalysis(AnalysisRequestDTO request) {
        String bodyPart = request.getBodyPart();
        Map<String, String> answers = request.getAnswers();

        // 1. Format answers into a readable string for the AI
        StringBuilder userContext = new StringBuilder();
        answers.forEach((question, answer) ->
                userContext.append("Q: ").append(question).append(" | A: ").append(answer).append("\n")
        );

        String systemPrompt = """
                You are a highly experienced, evidence-based dermatology analysis AI.
                
                Your task is to analyze structured questionnaire answers related to the user's %s and produce a precise, consistent, and strictly formatted JSON output for use in a professional dashboard.
                
                Guidelines:
                - Base your analysis ONLY on the provided answers.
                - Do NOT assume missing information.
                - Be conservative and realistic in scoring (avoid extreme values unless clearly justified).
                - Treat sensitivity and irritation as high-risk factors.
                - Prioritize long-term skin health over short-term cosmetic results.
                
                Scoring Logic:
                - healthScore (0–100):
                  90–100 = Excellent (healthy, balanced, minimal issues)
                  70–89  = Good (minor manageable concerns)
                  50–69  = Moderate (visible issues needing attention)
                  30–49  = Poor (significant concerns)
                  0–29   = Critical (severe issues)
                
                Analysis Rules:
                - Acne level:
                  High → frequent, cystic, inflamed, or widespread breakouts
                  Moderate → recurring but controlled acne
                  Low → rare or minimal breakouts
                
                - Pigmentation level:
                  High → dark spots, uneven tone, visible discoloration
                  Moderate → some uneven tone or mild spots
                  Low → even skin tone
                
                - Texture:
                  Dehydrated → tight, flaky, lacking moisture
                  Rough → bumpy, uneven, keratosis, clogged pores
                  Balanced → smooth and hydrated
                
                - suggestedPath:
                  Natural → mild issues, sensitive skin, prevention focus
                  Chemical → acne, pigmentation, oil control, active treatment needed
                  Ayurvedic → chronic, sensitivity-prone, holistic preference patterns
                
                Strict Output Rules:
                - Return ONLY valid JSON
                - No explanations, no markdown, no extra text
                - All fields are REQUIRED
                - Use only the allowed enum values
                
                JSON Schema:
                {
                  "healthScore": number,
                  "suggestedPath": "Natural | Chemical | Ayurvedic",
                  "markers": {
                    "acne": "High | Moderate | Low",
                    "pigment": "High | Moderate | Low",
                    "texture": "Dehydrated | Balanced | Rough"
                  },
                  "confidence": number (0-1),
                  "priorityConcerns": ["string", "string"],
                  "notes": "short clinical summary (max 25 words)"
                }
                
                Now analyze the following user answers:
                %s
                """.formatted(bodyPart, answers);

        // 3. Construct Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(groqApiKey);

        // 4. Construct Groq Payload using Maps (Safer than manual String replacement)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama-3.3-70b-versatile");
        requestBody.put("temperature", 0.2);

        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", userContext.toString())
        );
        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 5. Call Groq and Parse Response
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, entity, Map.class);

            if (response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");

                String rawContent = (String) message.get("content");

                String cleanJson = rawContent.replace("```json", "").replace("```", "").trim();

                return cleanJson;
            } else {
                throw new RuntimeException("Received empty response from Groq API.");
            }

        } catch (Exception e) {
            log.error("Error calling Groq API for skin analysis: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to analyze skin data via AI.");
        }
    }
}