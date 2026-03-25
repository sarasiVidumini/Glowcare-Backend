package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * AdminRepository
 * Exclusive data access for the SuperAdmin account (admin@glowcare.ai).
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Finds the SuperAdmin by their unique email.
     * Used during the login process to verify the main actor.
     * @param email The admin email address
     * @return Optional containing the SuperAdmin if found
     */
    Optional<Admin> findByEmail(String email);

    /**
     * Checks if a SuperAdmin exists with the given email.
     * Useful for initial system setup or validation.
     */
    boolean existsByEmail(String email);
}