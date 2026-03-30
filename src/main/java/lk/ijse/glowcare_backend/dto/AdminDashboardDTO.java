package lk.ijse.glowcare_backend.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    private long totalUsers;
    private long totalExperts;
    private long totalAppointments;
    private long totalRoutineProducts;
    private long totalAiAnalyses;
    private long totalActiveTreatments;

    // 🚀 ADD THIS LINE TO FIX THE BUILDER ERROR
    private double systemEfficiency;

    private List<RecentActivityDTO> activities;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentActivityDTO {
        private String title;
        private String timestamp;
        private String status;
    }
}