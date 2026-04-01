package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.RoutineConflictRequestDTO;
import lk.ijse.glowcare_backend.dto.RoutineConflictResponseDTO;
import lk.ijse.glowcare_backend.service.SmartEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/smart-engine")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SmartEngineController {

    private final SmartEngineService smartEngineService;

    @PostMapping("/check-conflict")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoutineConflictResponseDTO> checkConflict(@RequestBody RoutineConflictRequestDTO request) {
        return ResponseEntity.ok(smartEngineService.checkConflict(request));
    }
}