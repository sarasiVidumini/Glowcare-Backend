package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.RoutineDTO;
import lk.ijse.triglowglowcare_backend.service.impl.RoutineServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/routines")
public class RoutineController {

    private final RoutineServiceImpl routineServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveRoutine(@RequestBody RoutineDTO routineDTO) {
        routineServiceImpl.saveRoutine(routineDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Routine saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateRoutine(@RequestBody RoutineDTO routineDTO) {
        routineServiceImpl.updateRoutine(routineDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Routine updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<APIResponse<String>> deleteRoutine(@PathVariable long routineId) {
        routineServiceImpl.deleteRoutine(routineId);
        return new ResponseEntity<>(new APIResponse<>(200,"Routine deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<RoutineDTO>>> getAllRoutines() {
        List<RoutineDTO> routines = routineServiceImpl.getAllRoutines();
        return new ResponseEntity<>(new APIResponse<>(200,"Routine List Successfully",routines), HttpStatus.OK);
    }
}
