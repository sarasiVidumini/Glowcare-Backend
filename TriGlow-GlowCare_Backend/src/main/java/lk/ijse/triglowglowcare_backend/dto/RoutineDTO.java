package lk.ijse.triglowglowcare_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "RoutineRepo type is required")
    private String routineType; // MORNING, NIGHT

    @NotBlank(message = "Target body part is required")
    private String targetBodyPart; // FACE, HAIR, HANDS, LEGS

    private Boolean isActive;
}
