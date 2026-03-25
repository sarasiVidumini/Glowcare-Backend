package lk.ijse.glowcare_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminDashboardDTO {

    private long totalUsers;
    private long totalExperts;
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
