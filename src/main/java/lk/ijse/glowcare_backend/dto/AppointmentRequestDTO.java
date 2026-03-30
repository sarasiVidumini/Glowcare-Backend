package lk.ijse.glowcare_backend.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentRequestDTO {
    private String userEmail; // 🚀 Changed to use Email instead of ID
    private Long physicianId;
    private String date;
    private String reason;
}