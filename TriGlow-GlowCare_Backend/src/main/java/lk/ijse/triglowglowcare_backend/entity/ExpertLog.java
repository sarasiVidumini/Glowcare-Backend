package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "expert_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExpertLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    public User user;

    @Column(nullable = false)
    private String userNameAtConsultation;

    @Column(name = "login_time", insertable = false, updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

    private Timestamp loginTime;
}
