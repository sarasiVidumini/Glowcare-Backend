package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.RoutineStepDTO;
import lk.ijse.glowcare_backend.entity.RoutineStep;
import lk.ijse.glowcare_backend.repository.RoutineStepRepository;
import lk.ijse.glowcare_backend.service.RoutineStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineStepServiceImpl implements RoutineStepService {

    private final RoutineStepRepository repository;

    @Override
    public RoutineStepDTO createStep(RoutineStepDTO dto) {
        RoutineStep step = RoutineStep.builder()
                .productName(dto.getProductName())
                .scheduledTime(dto.getScheduledTime())
                .timeOfDay(dto.getTimeOfDay())
                // Map the new fields
                .pathCategory(dto.getPathCategory())
                .zone(dto.getZone())
                .build();

        RoutineStep savedStep = repository.save(step);
        return mapToDTO(savedStep);
    }

    @Override
    public List<RoutineStepDTO> getAllSteps() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoutineStepDTO> getStepsByTimeOfDay(String timeOfDay) {
        return repository.findByTimeOfDayIgnoreCase(timeOfDay).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoutineStepDTO updateStep(Long id, RoutineStepDTO dto) {
        RoutineStep existingStep = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formula not found"));

        existingStep.setProductName(dto.getProductName());
        existingStep.setScheduledTime(dto.getScheduledTime());
        existingStep.setTimeOfDay(dto.getTimeOfDay());
        // Update the new fields
        existingStep.setPathCategory(dto.getPathCategory());
        existingStep.setZone(dto.getZone());

        RoutineStep updatedStep = repository.save(existingStep);
        return mapToDTO(updatedStep);
    }

    @Override
    public void deleteStep(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Formula not found");
        }
        repository.deleteById(id);
    }

    private RoutineStepDTO mapToDTO(RoutineStep entity) {
        return RoutineStepDTO.builder()
                .id(entity.getId())
                .productName(entity.getProductName())
                .scheduledTime(entity.getScheduledTime())
                .timeOfDay(entity.getTimeOfDay())
                // Map the new fields back to DTO
                .pathCategory(entity.getPathCategory())
                .zone(entity.getZone())
                .build();
    }
}