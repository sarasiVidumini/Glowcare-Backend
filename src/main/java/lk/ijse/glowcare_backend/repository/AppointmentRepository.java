package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
