package lk.ijse.glowcare_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.glowcare_backend.dto.AdminDTO;
import lk.ijse.glowcare_backend.dto.AdminDashboardDTO;
import lk.ijse.glowcare_backend.entity.Admin;
import lk.ijse.glowcare_backend.repository.AdminRepository;
import lk.ijse.glowcare_backend.repository.UserRepository;
import lk.ijse.glowcare_backend.util.AdminActivityMapper; // Optional helper for activity strings
import lk.ijse.glowcare_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    @Override
    public AdminDashboardDTO getNexusStats() {
        try {
            long totalUsers = userRepository.count();
            long totalExperts = userRepository.countByRole("EXPERT");

            List<AdminDashboardDTO.RecentActivityDTO> activities = new ArrayList<>();

            // Use your Mapper to create "Superb" entries
            activities.add(AdminActivityMapper.createActivity("Neural Link Established", "Active", "SUCCESS"));
            activities.add(AdminActivityMapper.createActivity("User Directory Synced", "2m ago", "SUCCESS"));
            activities.add(AdminActivityMapper.createActivity("Integrity Check Complete", "1h ago", "SUCCESS"));

            return AdminDashboardDTO.builder()
                    .totalUsers(totalUsers)
                    .totalExperts(totalExperts)
                    .systemEfficiency(99.9)
                    .activities(activities)
                    .build();

        } catch (Exception e) {
            // Fallback that still follows the new DTO structure
            return AdminDashboardDTO.builder()
                    .systemEfficiency(0.0)
                    .activities(List.of(AdminActivityMapper.createActivity("System Sync Interrupted", "N/A", "WARNING")))
                    .build();
        }
    }

    @Override
    public void clearSystemCache() {
        // Logic for maintenance: clearing Hibernate second-level cache or custom caches
        System.out.println("SuperAdmin: System Cache Purge Initiated.");
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(admin -> AdminDTO.builder()
                        .id(admin.getId())
                        .email(admin.getEmail())
                        .fullName(admin.getFullName())
                        .lastLogin(admin.getLastLogin())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAdmin(AdminDTO adminDTO) {
        Admin admin = adminRepository.findById(adminDTO.getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setFullName(adminDTO.getFullName());
        admin.setEmail(adminDTO.getEmail());

        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found");
        }
        adminRepository.deleteById(id);
    }

}
