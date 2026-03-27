package lk.ijse.glowcare_backend.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RoutineNotificationsDTO {

    private Long id;
    private String title;
    private String message;
    private boolean isActive;

    // Relational Data from RoutineStep
    private Long routineStepId;
    private String routineStepName;
    private String scheduledTime;
    private String pathCategory;

}