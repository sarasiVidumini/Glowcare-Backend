package lk.ijse.triglowglowcare_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HospitalDTO {
    private Long id;

    @NotBlank(message = "Hospital name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

}

