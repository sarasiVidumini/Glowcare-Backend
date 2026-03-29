package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicianDTO {
    private Long id;
    private String name;
    private String specialization;
    private String hospitalName;
    private String city;
    private String availableDays;
    private Double latitude;
    private Double longitude;
}