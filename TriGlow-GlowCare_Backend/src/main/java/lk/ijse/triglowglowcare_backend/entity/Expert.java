package lk.ijse.triglowglowcare_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "experts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Expert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(nullable = false)
   private String fullName;

    @Column(nullable = false , unique = true)
    private String email;

    @Column(nullable = false , unique = true)
    private String license;

    @Column(nullable = false , unique = true)
    private String password;


}
