package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.RoutineStepDTO;

import java.util.List;

public interface RoutineStepService {

    RoutineStepDTO createStep(RoutineStepDTO dto);
    List<RoutineStepDTO> getAllSteps();
    List<RoutineStepDTO> getStepsByTimeOfDay(String timeOfDay);
    RoutineStepDTO updateStep(Long id, RoutineStepDTO dto);
    void deleteStep(Long id);

}
