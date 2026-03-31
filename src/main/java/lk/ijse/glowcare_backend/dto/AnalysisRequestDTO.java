package lk.ijse.glowcare_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnalysisRequestDTO {
    private String bodyPart;
    private Map<String, String> answers;
}
