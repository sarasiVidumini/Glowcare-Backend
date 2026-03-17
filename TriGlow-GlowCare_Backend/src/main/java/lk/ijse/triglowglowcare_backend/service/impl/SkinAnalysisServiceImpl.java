package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.SkinAnalysisDTO;
import lk.ijse.triglowglowcare_backend.entity.SkinAnalysis;
import lk.ijse.triglowglowcare_backend.repository.SkinAnalysisRepo;
import lk.ijse.triglowglowcare_backend.service.SkinAnalysisService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SkinAnalysisServiceImpl implements SkinAnalysisService {

    private final SkinAnalysisRepo skinAnalysisRepo;
    private final ModelMapper modelMapper;

    @Override
    public void saveSkinAnalysis(SkinAnalysisDTO skinAnalysisDTO) {
        skinAnalysisRepo.save(modelMapper.map(skinAnalysisDTO, SkinAnalysis.class));
    }

    @Override
    public void updateSkinAnalysis(SkinAnalysisDTO skinAnalysisDTO) {
        skinAnalysisRepo.save(modelMapper.map(skinAnalysisDTO, SkinAnalysis.class));
    }

    @Override
    public void deleteSkinAnalysis(long skinAnalysisId) {
        skinAnalysisRepo.deleteById(skinAnalysisId);
    }

    @Override
    public List<SkinAnalysisDTO> getAllSkinAnalysis() {
        List<SkinAnalysis> skinAnalyses = skinAnalysisRepo.findAll();
        return modelMapper.map(skinAnalyses,new TypeToken<List<SkinAnalysisDTO>>() {}.getType());
    }
}
