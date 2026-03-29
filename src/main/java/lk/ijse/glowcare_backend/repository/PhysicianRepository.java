package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.Physician;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {
    List<Physician> findByCityContainingIgnoreCase(String city);
}