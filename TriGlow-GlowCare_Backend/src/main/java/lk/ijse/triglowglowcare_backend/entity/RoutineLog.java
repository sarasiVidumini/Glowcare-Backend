package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "routine_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RoutineLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id" , nullable = false)
    private Routine routine;

    @Column(nullable = false)
    private LocalDate completionDate;

    private Boolean isCompleted = false;
}
