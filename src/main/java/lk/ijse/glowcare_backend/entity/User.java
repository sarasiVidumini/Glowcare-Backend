package lk.ijse.glowcare_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // "user" is a reserved keyword in some databases (like PostgreSQL), so we use "users"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    // This can be null if they sign in with Google!
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // --- Google OAuth2 Tracking Fields ---
    private String authProvider; // e.g., "LOCAL" or "GOOGLE"
    private String providerId;   // Google's unique subject ID
}