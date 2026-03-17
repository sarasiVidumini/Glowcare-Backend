package lk.ijse.triglowglowcare_backend.service;

import lk.ijse.triglowglowcare_backend.dto.RoutineDTO;
import lk.ijse.triglowglowcare_backend.dto.RoutineLogDTO;

import java.util.List;

public interface RoutineLogService {

    public void saveRoutineLog(RoutineLogDTO routineLogDTO);
    public void updateRoutineLog(RoutineLogDTO routineLogDTO);
    public void deleteRoutineLog(long routineLogId);
    public List<RoutineLogDTO> getAllRoutineLogs();

}
