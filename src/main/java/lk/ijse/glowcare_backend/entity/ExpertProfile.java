package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expert_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpertProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Expert specific fields
    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(length = 1000)
    private String bio;
    private String expertiseArea; // e.g., Ayurvedic Skincare, Cosmetology
}