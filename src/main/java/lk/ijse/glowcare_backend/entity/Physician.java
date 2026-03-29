package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "physicians")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String specialization;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String city;

    @Column(name = "available_days")
    private String availableDays;

    private Double latitude;

    private Double longitude;
}