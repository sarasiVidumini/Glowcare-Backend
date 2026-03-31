package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skin_questions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SkinQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bodyPart;
    private String questionText;
    @Column(columnDefinition = "json")
    private String options;
}