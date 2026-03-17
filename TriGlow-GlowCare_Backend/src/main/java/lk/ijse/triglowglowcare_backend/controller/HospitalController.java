package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.HospitalDTO;
import lk.ijse.triglowglowcare_backend.service.impl.HospitalServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hospitals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Validated

public class HospitalController {
    private final HospitalServiceImpl hospitalServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveHospital(@RequestBody HospitalDTO hospitalDTO) {
        hospitalServiceImpl.saveHospital(hospitalDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Hospital saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateHospital(@RequestBody HospitalDTO hospitalDTO) {
        hospitalServiceImpl.updateHospital(hospitalDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Hospital updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<APIResponse<String>> deleteHospital(@PathVariable long hospitalId) {
        hospitalServiceImpl.deleteHospital(hospitalId);
        return new ResponseEntity<>(new APIResponse<>(200,"Hospital deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<HospitalDTO>>> getAllHospitals() {
        List<HospitalDTO> hospitals = hospitalServiceImpl.getAllHospitals();
        return new ResponseEntity<>(new APIResponse<>(200,"Hospital List Successfully",hospitals), HttpStatus.OK);
    }
}
