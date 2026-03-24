package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserProfilesDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;

}
