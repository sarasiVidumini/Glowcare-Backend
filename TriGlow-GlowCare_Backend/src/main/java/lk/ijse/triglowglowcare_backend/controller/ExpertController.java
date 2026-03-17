package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.ExpertDTO;
import lk.ijse.triglowglowcare_backend.entity.Expert;
import lk.ijse.triglowglowcare_backend.service.impl.ExpertServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/experts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated

public class ExpertController {

    private final ExpertServiceImpl expertServiceImpl;

    @PostMapping
        public ResponseEntity<APIResponse<String>> saveExpert(@RequestBody ExpertDTO expertDTO) throws Exception {
        expertServiceImpl.saveExpert(expertDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Expert saved Successfully", null), HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateExpert(@RequestBody ExpertDTO expertDTO) throws Exception {
        expertServiceImpl.updateExpert(expertDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Expert updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{expertId}")
    public ResponseEntity<APIResponse<String>> deleteExpert(@PathVariable long expertId) throws Exception {
        expertServiceImpl.deleteExpert(expertId);
        return new ResponseEntity<>(new APIResponse<>(200,"Expert deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ExpertDTO>>> getAllExperts() throws Exception {
        List<ExpertDTO> experts = expertServiceImpl.getAllExperts();
        return new ResponseEntity<>(new APIResponse<>(200,"Expert List Successfully",experts), HttpStatus.OK);
    }

}

