package lk.ijse.triglowglowcare_backend.repository;

import lk.ijse.triglowglowcare_backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
}
