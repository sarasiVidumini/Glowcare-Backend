package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class RoutineStepDTO {

    private Long id;
    private String productName;
    private String scheduledTime;
    private String timeOfDay;
    private String pathCategory;
    private String zone;
}
