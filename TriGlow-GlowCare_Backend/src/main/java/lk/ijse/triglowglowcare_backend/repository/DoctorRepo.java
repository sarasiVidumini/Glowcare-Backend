package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {
}
