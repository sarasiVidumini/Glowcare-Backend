package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineConflictResponseDTO {
    private boolean hasConflict;
    private String reason;
    private String original;
    private String alternative;
}