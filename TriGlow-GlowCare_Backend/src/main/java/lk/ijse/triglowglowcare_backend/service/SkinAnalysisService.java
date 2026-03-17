package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.SkinAnalysisDTO;
import lk.ijse.triglowglowcare_backend.entity.SkinAnalysis;

import java.util.List;

public interface SkinAnalysisService {

    public void saveSkinAnalysis(SkinAnalysisDTO skinAnalysisDTO);

    public void updateSkinAnalysis(SkinAnalysisDTO skinAnalysisDTO);

    public void deleteSkinAnalysis(long skinAnalysisId);

    public List<SkinAnalysisDTO> getAllSkinAnalysis();

}
