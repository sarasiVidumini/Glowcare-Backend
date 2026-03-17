package lk.ijse.triglowglowcare_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExpertLogDTO {
    private Long id;

    @NotNull(message = "User ID is required to log in")
    private Long userId;

    @NotBlank(message = "Please enter your name to proceed")
    private String userNameAtConsultation;

    private Timestamp loginTime;
}
