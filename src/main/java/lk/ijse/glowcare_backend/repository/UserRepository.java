package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA magically writes the SQL query for this!
    Optional<User> findByEmail(String email);
    /**
     * Counts the number of accounts based on their role.
     * Used by AdminService to populate the "Expert Network" and "Total Entities" cards.
     * @param role The role string (e.g., "expert", "user")
     * @return count of users with that specific role
     */
    long countByRole(String role);


}