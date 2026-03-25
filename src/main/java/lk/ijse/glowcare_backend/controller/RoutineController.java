package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.RoutineStepDTO;
import lk.ijse.glowcare_backend.service.RoutineStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoutineController {

    private final RoutineStepService routineService;


    @PostMapping
    public ResponseEntity<RoutineStepDTO> createStep(@RequestBody RoutineStepDTO dto) {
        return new ResponseEntity<>(routineService.createStep(dto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<RoutineStepDTO>> getAllSteps() {
        return ResponseEntity.ok(routineService.getAllSteps());
    }


    @GetMapping("/filter")
    public ResponseEntity<List<RoutineStepDTO>> getStepsByTimeOfDay(@RequestParam String timeOfDay) {
        return ResponseEntity.ok(routineService.getStepsByTimeOfDay(timeOfDay));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RoutineStepDTO> updateStep(@PathVariable Long id, @RequestBody RoutineStepDTO dto) {
        return ResponseEntity.ok(routineService.updateStep(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStep(@PathVariable Long id) {
        routineService.deleteStep(id);
        return ResponseEntity.ok("Formula deleted successfully");
    }
}
