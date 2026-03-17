package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "skin_analysis")
@Getter
@Setter
@NoArgsConstructor

public class SkinAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    public User user;

    @Enumerated(EnumType.STRING)
    private Target_Body_Part body_part;

    private String imageUrl;
    private String detectedCondition;

    @Enumerated(EnumType.STRING)
    private Treatment_Category suggestedTreatmentType;

    @Column(name = "analysis_date" , insertable = false , updatable = false)
    private Timestamp analysisDate;


}
