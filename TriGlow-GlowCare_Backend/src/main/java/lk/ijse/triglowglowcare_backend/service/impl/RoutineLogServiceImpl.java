package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.RoutineDTO;
import lk.ijse.triglowglowcare_backend.dto.RoutineLogDTO;
import lk.ijse.triglowglowcare_backend.entity.Routine;
import lk.ijse.triglowglowcare_backend.entity.RoutineLog;
import lk.ijse.triglowglowcare_backend.repository.RoutineLogRepo;
import lk.ijse.triglowglowcare_backend.service.RoutineLogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class RoutineLogServiceImpl implements RoutineLogService {

    private final RoutineLogRepo routineLogRepo;
    private final ModelMapper modelMapper;


    @Override
    public void saveRoutineLog(RoutineLogDTO routineLogDTO) {
        routineLogRepo.save(modelMapper.map(routineLogDTO, RoutineLog.class));

    }

    @Override
    public void updateRoutineLog(RoutineLogDTO routineLogDTO) {
        routineLogRepo.save(modelMapper.map(routineLogDTO, RoutineLog.class));
    }

    @Override
    public void deleteRoutineLog(long routineLogId) {
        routineLogRepo.deleteById(routineLogId);
    }

    @Override
    public List<RoutineLogDTO> getAllRoutineLogs() {
        List<RoutineLog> routineLogs = routineLogRepo.findAll();
        return modelMapper.map(routineLogs,new TypeToken<List<RoutineLogDTO>>() {}.getType());
    }
}
