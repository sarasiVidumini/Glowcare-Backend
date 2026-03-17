package lk.ijse.triglowglowcare_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicChatDTO {
    private Long id;

    @NotNull(message = "Sender ID is required")
    private Long senderId;

    @NotNull(message = "Sender Name is required")
    private String senderName; // Null if public chat

    @NotBlank(message = "Message content cannot be blank")
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp timestamp;

}
