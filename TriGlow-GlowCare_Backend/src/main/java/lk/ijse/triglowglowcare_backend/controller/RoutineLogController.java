package lk.ijse.triglowglowcare_backend.controller;

import lk.ijse.triglowglowcare_backend.dto.RoutineLogDTO;
import lk.ijse.triglowglowcare_backend.entity.RoutineLog;
import lk.ijse.triglowglowcare_backend.service.impl.RoutineLogServiceImpl;
import lk.ijse.triglowglowcare_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/routineLogs")
@CrossOrigin(origins = "*")
@Validated

public class RoutineLogController {

    private final RoutineLogServiceImpl routineLogServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveRoutineLog(@RequestBody RoutineLogDTO routineLogDTO) {

        routineLogServiceImpl.saveRoutineLog(routineLogDTO);
        return new ResponseEntity<>(new APIResponse<>(201,"Routine Log saved Successfully",null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateRoutineLog(@RequestBody RoutineLogDTO routineLogDTO) {
        routineLogServiceImpl.updateRoutineLog(routineLogDTO);
        return new ResponseEntity<>(new APIResponse<>(200,"Routine Log updated Successfully",null), HttpStatus.OK);
    }

    @DeleteMapping("/{routineLogId}")
    public ResponseEntity<APIResponse<String>> deleteRoutineLog(@PathVariable long routineLogId) {
        routineLogServiceImpl.deleteRoutineLog(routineLogId);
        return new ResponseEntity<>(new APIResponse<>(200,"Routine Log deleted Successfully",null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<RoutineLogDTO>>> getAllRoutineLogs() {
        List<RoutineLogDTO> routineLogs = routineLogServiceImpl.getAllRoutineLogs();
        return new ResponseEntity<>(new APIResponse<>(200,"Customer List Successfully",routineLogs), HttpStatus.OK);
    }
}
