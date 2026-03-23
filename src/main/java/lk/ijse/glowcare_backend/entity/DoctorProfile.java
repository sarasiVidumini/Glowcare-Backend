package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "doctor_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Doctor specific fields
    @Column(nullable = false, unique = true)
    private String licenseNumber; // e.g., SLMC Registration Number

    private String specialization; // e.g., Dermatologist
    private String hospitalOrClinic;
    private String contactNumber;
}