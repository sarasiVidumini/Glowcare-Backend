package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.*;
import lk.ijse.glowcare_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/nexus-stats")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<AdminDashboardDTO> getNexusStats() {
        return ResponseEntity.ok(adminService.getNexusStats());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<List<AdminDTO>> getAll() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<String> update(@RequestBody AdminDTO dto) {
        adminService.updateAdmin(dto);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok("Deleted");
    }
}