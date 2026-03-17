package lk.ijse.triglowglowcare_backend.service.impl;

import lk.ijse.triglowglowcare_backend.dto.RoutineDTO;
import lk.ijse.triglowglowcare_backend.entity.Routine;
import lk.ijse.triglowglowcare_backend.repository.RoutineRepo;
import lk.ijse.triglowglowcare_backend.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepo routineRepo;
    private final ModelMapper modelMapper;


    @Override
    public void saveRoutine(RoutineDTO routineDTO) {
        routineRepo.save(modelMapper.map(routineDTO , Routine.class));
    }

    @Override
    public void updateRoutine(RoutineDTO routineDTO) {
        routineRepo.save(modelMapper.map(routineDTO , Routine.class));
    }

    @Override
    public void deleteRoutine(long routineId) {
        routineRepo.deleteById(routineId);
    }

    @Override
    public List<RoutineDTO> getAllRoutines() {
        List<Routine> routines = routineRepo.findAll();
        return modelMapper.map(routines,new TypeToken<List<RoutineDTO>>() {}.getType());
    }
}
