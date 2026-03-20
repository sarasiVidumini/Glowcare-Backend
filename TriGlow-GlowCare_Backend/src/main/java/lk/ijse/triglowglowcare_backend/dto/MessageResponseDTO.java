package lk.ijse.triglowglowcare_backend.dto;

import lk.ijse.triglowglowcare_backend.entity.Chat_Type;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MessageResponseDTO {

    private Long id;
    private String senderEmail;
    private String content;
    private Chat_Type chatType;
    private LocalDateTime timestamp;
    private boolean isEdited;

}
