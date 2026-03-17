package lk.ijse.triglowglowcare_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkinAnalysisDTO {
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Body part must be specified")
    private String bodyPart;

    private String imageUrl;
    private String detectedCondition;
    private String suggestedTreatmentType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp analysisDate;
}
