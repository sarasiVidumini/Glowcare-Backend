package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "public_chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Joins directly to the verified user in the database
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 2000)
    private String content;

    private String fileUrl;

    @Column(nullable = false)
    private boolean isEdited = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    public enum MessageType { CHAT, JOIN, LEAVE, EDIT, DELETE }

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}