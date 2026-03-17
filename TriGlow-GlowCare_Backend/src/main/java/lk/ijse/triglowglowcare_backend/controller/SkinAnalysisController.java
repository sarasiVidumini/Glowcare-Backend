package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.SkinAnalysisDTO;
import lk.ijse.triglowglowcare_backend.service.impl.SkinAnalysisServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/skinAnalysis")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated

public class SkinAnalysisController {

    private final SkinAnalysisServiceImpl skinAnalysisServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveSkinAnalysis(@RequestBody SkinAnalysisDTO skinAnalysisDTO) {
        skinAnalysisServiceImpl.saveSkinAnalysis(skinAnalysisDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Skin Analysis saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateSkinAnalysis(@RequestBody SkinAnalysisDTO skinAnalysisDTO) {
        skinAnalysisServiceImpl.updateSkinAnalysis(skinAnalysisDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Skin Analysis updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{skinAnalysisId}")
    public ResponseEntity<APIResponse<String>> deleteCustomer(@PathVariable long skinAnalysisId) {
        skinAnalysisServiceImpl.deleteSkinAnalysis(skinAnalysisId);
        return new ResponseEntity<>(new APIResponse<>(200,"Skin Analysis deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<SkinAnalysisDTO>>> getAllSkinAnalysis() {
        List<SkinAnalysisDTO> skinAnalysis = skinAnalysisServiceImpl.getAllSkinAnalysis();
        return new ResponseEntity<>(new APIResponse<>(200,"Skin Analysis List Successfully",skinAnalysis), HttpStatus.OK);
    }
}
