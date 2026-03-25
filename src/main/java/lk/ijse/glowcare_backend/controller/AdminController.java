package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.AdminDTO;
import lk.ijse.glowcare_backend.dto.AdminDashboardDTO;
import lk.ijse.glowcare_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/nexus-stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AdminDashboardDTO> getNexusStats() {
        return ResponseEntity.ok(adminService.getNexusStats());
    }

    @PostMapping("/reboot-cache")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> rebootCache() {
        adminService.clearSystemCache();
        return ResponseEntity.ok("Neural Cache Rejuvenated Successfully");
    }

    // --- Admin Management (For the Information Table) ---

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AdminDTO>> getAll() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> update(@RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(adminDTO);
        return ResponseEntity.ok("Admin profile updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}