package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.AnalysisRequestDTO;
import lk.ijse.glowcare_backend.entity.SkinQuestions;

import java.util.List;

public interface SkinAnalysisService {
    List<SkinQuestions> getQuestionsByBodyPart(String bodyPart);

    String generateSkinAnalysis(AnalysisRequestDTO request);
}
