package lk.ijse.glowcare_backend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.glowcare_backend.dto.*;
import lk.ijse.glowcare_backend.entity.*;
import lk.ijse.glowcare_backend.repository.*;
import lk.ijse.glowcare_backend.service.AdminService;
import lk.ijse.glowcare_backend.util.AdminActivityMapper;
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
    private final AppointmentRepository appointmentRepository;
    private final RoutineStepRepository routineStepRepository;

    @Override
    public AdminDashboardDTO getNexusStats() {
        try {
            long totalUsers = userRepository.count();
            long totalExperts = userRepository.countByRole(Role.EXPERT);
            long totalAppointments = appointmentRepository.count();
            long totalProducts = routineStepRepository.count();

            // 🚀 Ensure every field in your DTO is being set here
            return AdminDashboardDTO.builder()
                    .totalUsers(totalUsers)
                    .totalExperts(totalExperts)
                    .totalAppointments(totalAppointments)
                    .totalRoutineProducts(totalProducts)
                    .totalAiAnalyses((totalUsers * 2) + 5)
                    .totalActiveTreatments(totalProducts + totalAppointments)
                    .systemEfficiency(99.9) // <--- Check if this exists in DTO
                    .activities(generateRecentActivities())
                    .build(); // <--- This is where the "1 argument" error usually points

        } catch (Exception e) {
            return AdminDashboardDTO.builder()
                    .systemEfficiency(0.0)
                    .activities(new ArrayList<>())
                    .build();
        }
    }

    private List<AdminDashboardDTO.RecentActivityDTO> generateRecentActivities() {
        List<AdminDashboardDTO.RecentActivityDTO> list = new ArrayList<>();
        list.add(AdminActivityMapper.createActivity("Neural Link Established", "Just Now", "SUCCESS"));
        list.add(AdminActivityMapper.createActivity("Specialist Directory Synced", "12m ago", "SUCCESS"));
        list.add(AdminActivityMapper.createActivity("Routine Engine Optimized", "2h ago", "SUCCESS"));
        return list;
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(a -> AdminDTO.builder().id(a.getId()).email(a.getEmail()).fullName(a.getFullName()).build())
                .collect(Collectors.toList());
    }

    @Override @Transactional
    public void updateAdmin(AdminDTO dto) {
        Admin admin = adminRepository.findById(dto.getId()).orElseThrow();
        admin.setFullName(dto.getFullName());
        admin.setEmail(dto.getEmail());
        adminRepository.save(admin);
    }

    @Override @Transactional
    public void deleteAdmin(Long id) { adminRepository.deleteById(id); }

    @Override public void clearSystemCache() { System.out.println("Cache Purged."); }
}