package lk.ijse.triglowglowcare_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Hospital ID is required")
    private Long hospitalId;

    @NotNull
    private String specialization;

    @NotNull
    private String availableDays;
}

