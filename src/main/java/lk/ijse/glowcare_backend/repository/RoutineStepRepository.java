package lk.ijse.glowcare_backend.repository;

import lk.ijse.glowcare_backend.entity.RoutineStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineStepRepository extends JpaRepository<RoutineStep, Long> {

    // Custom query to filter formulas by the active tab
    List<RoutineStep> findByTimeOfDayIgnoreCase(String timeOfDay);

    List<RoutineStep> findByPathCategoryAndZoneAndTimeOfDay(String pathCategory, String zone, String timeOfDay);

}
