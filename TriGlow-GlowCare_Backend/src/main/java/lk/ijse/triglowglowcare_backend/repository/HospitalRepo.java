package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepo extends JpaRepository<Hospital, Long> {
}
