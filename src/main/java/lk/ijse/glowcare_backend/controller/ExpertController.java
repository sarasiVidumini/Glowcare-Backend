package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.ExpertDTO;
import lk.ijse.glowcare_backend.service.ExpertProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experts")
@RequiredArgsConstructor
// Updated CrossOrigin to be more specific to your React dev environment
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ExpertController {

    private final ExpertProfileService expertService;

    /**
     * 1. GET ALL EXPERTS
     * Accessible by everyone to view the Clinical Network.
     */
    @GetMapping
    public ResponseEntity<List<ExpertDTO>> getAllExperts() {
        List<ExpertDTO> experts = expertService.getAllExperts();
        return ResponseEntity.ok(experts);
    }

    /**
     * 2. GET EXPERT BY ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpertDTO> getExpertById(@PathVariable Long id) {
        ExpertDTO expert = expertService.getExpertById(id);
        return ResponseEntity.ok(expert);
    }

    /**
     * 3. CREATE EXPERT (Admin Override)
     * Note: Most experts will now be created via AuthController/Register.
     * This remains for Admin manual additions if necessary.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpertDTO> createExpert(@RequestBody ExpertDTO expertDTO) {
        ExpertDTO createdExpert = expertService.createExpert(expertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpert);
    }

    /**
     * 4. UPDATE EXPERT
     * Allows an expert to update their specialty or bio.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpert(
            @PathVariable Long id,
            @RequestBody ExpertDTO expertDTO) {

        expertDTO.setId(id);
        expertService.updateExpert(expertDTO);
        return ResponseEntity.ok("Expert Profile Updated Successfully");
    }

    /**
     * 5. DELETE EXPERT
     * Only Admins can revoke clinical access.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteExpert(@PathVariable Long id) {
        expertService.deleteExpert(id);
        // Changed to Void + NO_CONTENT for standard REST practice
        return ResponseEntity.noContent().build();
    }
}