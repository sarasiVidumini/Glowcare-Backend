package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.RoutineNotifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineNotificationRepository extends JpaRepository<RoutineNotifications , Long> {

    List<RoutineNotifications> findByIsActiveTrue();

}
