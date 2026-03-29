package lk.ijse.glowcare_backend.dto;
import lombok.Data;

@Data
public class AppointmentRequestDTO {
    private String userEmail; // 🚀 Changed to use Email instead of ID
    private Long physicianId;
    private String date;
    private String reason;
}