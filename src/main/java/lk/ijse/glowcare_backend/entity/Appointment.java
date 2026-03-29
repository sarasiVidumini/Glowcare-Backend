package lk.ijse.glowcare_backend.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "appointments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Appointment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false) // MUST match the SQL column name
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "physician_id", nullable = false)
    private Physician physician;

    private LocalDate appointmentDate;
    private String reason;
    private String status;
}