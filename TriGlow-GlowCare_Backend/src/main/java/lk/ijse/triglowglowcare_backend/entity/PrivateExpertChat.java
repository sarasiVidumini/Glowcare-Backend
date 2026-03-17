package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "private_expert_chats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PrivateExpertChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id" , nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id" , nullable = false)
    private User receiver;

    @Column(columnDefinition = "TEXT" , nullable = false)
    private String message;

    @Column(insertable = false,updatable = false)
    private Timestamp timestamp;
}
