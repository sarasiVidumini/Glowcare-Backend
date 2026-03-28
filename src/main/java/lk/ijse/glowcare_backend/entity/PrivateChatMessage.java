package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "private_chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivateChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Column(length = 2000)
    private String content;

    private String fileUrl;

    @Column(nullable = false)
    private Boolean isEdited = false;

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