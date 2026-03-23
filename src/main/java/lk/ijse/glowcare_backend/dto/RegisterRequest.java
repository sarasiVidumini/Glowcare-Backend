package lk.ijse.glowcare_backend.dto;

import lk.ijse.glowcare_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // ADMIN, CLIENT, DOCTOR, EXPERT
    private String licenseNumber;
}