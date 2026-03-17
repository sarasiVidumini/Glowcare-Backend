package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.RoutineDTO;
import lk.ijse.triglowglowcare_backend.entity.Routine;

import java.util.List;

public interface RoutineService {

    public void saveRoutine(RoutineDTO routineDTO);
    public void updateRoutine(RoutineDTO routineDTO);
    public void deleteRoutine(long routineId);
    public List<RoutineDTO> getAllRoutines();

}
