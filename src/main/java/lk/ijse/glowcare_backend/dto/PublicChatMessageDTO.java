package lk.ijse.glowcare_backend.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicChatMessageDTO {
    private Long id;
    private Long userId;
    private String senderName;

    // Identifies if the user is an ADMIN, CLIENT, or EXPERT
    private String role;

    private String content;
    private String fileUrl;

    // Capital 'B' Boolean prevents the Jackson Null Crash
    private Boolean isEdited;

    private MessageType type;
    private LocalDateTime timestamp;

    public enum MessageType { CHAT, JOIN, LEAVE, EDIT, DELETE }
}