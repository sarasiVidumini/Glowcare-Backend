package lk.ijse.glowcare_backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrivateChatMessageDTO {
    private Long id;
    private String roomId;
    private Long senderId;
    private Long receiverId;
    private String senderName;
    private String role;
    private String content;
    private String fileUrl;
    private Boolean isEdited;
    private MessageType type;
    private LocalDateTime timestamp;

    public enum MessageType { CHAT, JOIN, LEAVE, EDIT, DELETE }
}