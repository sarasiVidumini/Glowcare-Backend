package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "routines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    private LocalTime routineTime;

    @Enumerated(EnumType.STRING)
    private Routine_Types routineType;

    private Boolean isActive = true;

}
