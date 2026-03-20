package lk.ijse.triglowglowcare_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.triglowglowcare_backend.entity.Chat_Type;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MessageRequestDTO {

    private String content;
    private String senderEmail;
    private Chat_Type chat_type;
    private Long receiverId;

}
