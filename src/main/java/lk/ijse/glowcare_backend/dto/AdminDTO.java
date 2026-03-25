package lk.ijse.glowcare_backend.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * AdminDTO
 * Used for handling SuperAdmin profile data and authentication responses.
 * Excludes sensitive fields like password.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AdminDTO {

    private Long id;
    private String email;      // admin@glowcare.ai
    private String fullName;
    private LocalDateTime lastLogin;

    // Custom field to tell the frontend this is a SuperUser
    private final String role = "SUPER_ADMIN";
}