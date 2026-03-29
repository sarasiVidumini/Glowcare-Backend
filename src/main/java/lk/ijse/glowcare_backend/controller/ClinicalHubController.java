package lk.ijse.glowcare_backend.controller;
import lk.ijse.glowcare_backend.dto.*;
import lk.ijse.glowcare_backend.service.ClinicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clinical")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClinicalHubController {

    private final ClinicalService clinicalService;

    @GetMapping("/physicians")
    public ResponseEntity<List<PhysicianDTO>> searchPhysicians(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(clinicalService.getPhysiciansByCity(city));
    }

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestBody AppointmentRequestDTO requestDTO) {
        try {
            String result = clinicalService.bookAppointment(requestDTO);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // Sends the "Doctor is not available" error back to React
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🚀 1. ADD DOCTOR (Admin Only)
    @PostMapping("/physicians")
    public ResponseEntity<?> addPhysician(@RequestBody PhysicianDTO dto, Principal principal) {
        // Bulletproof Admin Check
        if (principal == null || !principal.getName().equals("admin@glowcare.ai")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Admin only.");
        }
        return ResponseEntity.ok(clinicalService.addPhysician(dto));
    }

    // 🚀 2. UPDATE DOCTOR (Admin Only)
    @PutMapping("/physicians/{id}")
    public ResponseEntity<?> updatePhysician(@PathVariable Long id, @RequestBody PhysicianDTO dto, Principal principal) {
        if (principal == null || !principal.getName().equals("admin@glowcare.ai")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Admin only.");
        }
        return ResponseEntity.ok(clinicalService.updatePhysician(id, dto));
    }

    // 🚀 3. DELETE DOCTOR (Admin Only)
    @DeleteMapping("/physicians/{id}")
    public ResponseEntity<?> deletePhysician(@PathVariable Long id, Principal principal) {
        if (principal == null || !principal.getName().equals("admin@glowcare.ai")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: Admin only.");
        }
        clinicalService.deletePhysician(id);
        return ResponseEntity.ok("Doctor deleted successfully.");
    }
}