package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.ExpertProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, Long> {

    boolean existsByLicenseNumber(String licenseNumber);
}