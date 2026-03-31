package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.AnalysisRequestDTO;
import lk.ijse.glowcare_backend.entity.SkinQuestions;
import lk.ijse.glowcare_backend.service.SkinAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analysis")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SkinAnalysisController {

    private final SkinAnalysisService analysisService;

    @GetMapping("/questions/{bodyPart}")
    public ResponseEntity<List<SkinQuestions>> getQuestions(@PathVariable String bodyPart) {
        List<SkinQuestions> questions = analysisService.getQuestionsByBodyPart(bodyPart);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeSkinData(@RequestBody AnalysisRequestDTO payload) {
        try {
            String aiJsonResult = analysisService.generateSkinAnalysis(payload);
            return ResponseEntity.ok(aiJsonResult);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}