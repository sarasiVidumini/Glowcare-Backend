package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoutineConflictRequestDTO {
    private String newProduct;
    private String timeOfDay;
    private String pathCategory;
    private String zone;
}