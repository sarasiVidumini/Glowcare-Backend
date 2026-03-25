package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "routine_steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;    // e.g., "HONEY CLEANSER"

    @Column(nullable = false)
    private String scheduledTime;  // e.g., "07:00 AM"

    @Column(nullable = false)
    private String timeOfDay;      // e.g., "MORNING" or "NIGHT"

    // --- NEW FIELDS ---
    @Column(nullable = false)
    private String pathCategory; // e.g., "Natural", "Chemical", "Ayurvedic"

    @Column(nullable = false)
    private String zone;         // e.g., "Face", "Hair", "Hands", "Leg"

}