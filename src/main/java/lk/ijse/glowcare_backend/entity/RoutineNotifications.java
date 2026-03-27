package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routine_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RoutineNotifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private boolean isActive;

    // --- THE JOIN TO ROUTINE STEPS ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "routine_step_id", nullable = false)
    private RoutineStep routineStep;

}