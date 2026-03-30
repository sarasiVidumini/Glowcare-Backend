package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.Role;
import lk.ijse.glowcare_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA magically writes the SQL query for this!
    Optional<User> findByEmail(String email);

    Optional<User> findFirstByNameIgnoreCase(String name);

    long countByRole(Role role);


}