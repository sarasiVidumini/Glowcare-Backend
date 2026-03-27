package lk.ijse.glowcare_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDTO {
    private Long id;
    private String fullName;
    private String email;
    private String licenseNumber;
    private String password;
    private String expertiseArea;
    private String bio;
}
