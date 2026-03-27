package lk.ijse.glowcare_backend.service.impl;

import lk.ijse.glowcare_backend.dto.RoutineNotificationsDTO;
import lk.ijse.glowcare_backend.entity.RoutineNotifications;
import lk.ijse.glowcare_backend.entity.RoutineStep;
import lk.ijse.glowcare_backend.repository.RoutineNotificationRepository;
import lk.ijse.glowcare_backend.repository.RoutineStepRepository;
import lk.ijse.glowcare_backend.service.RoutineNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineNotificationsServiceImpl implements RoutineNotificationsService {

    private final RoutineNotificationRepository notificationRepository;
    private final RoutineStepRepository routineStepRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoutineNotificationsDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoutineNotificationsDTO> getActiveNotifications() {
        return notificationRepository.findByIsActiveTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoutineNotificationsDTO createNotification(RoutineNotificationsDTO dto) {
        // 1. Fetch the RoutineStep to Join
        RoutineStep step = routineStepRepository.findById(dto.getRoutineStepId())
                .orElseThrow(() -> new RuntimeException("Routine Step not found with ID: " + dto.getRoutineStepId()));

        // 2. Build the Notification with the joined Step
        RoutineNotifications notification = RoutineNotifications.builder()
                .title(dto.getTitle())
                .message(dto.getMessage())
                .isActive(dto.isActive())
                .routineStep(step) // Make the Join!
                .build();

        // 3. Save and return DTO
        return mapToDTO(notificationRepository.save(notification));
    }

    @Override
    public RoutineNotificationsDTO updateNotification(Long id, RoutineNotificationsDTO dto) {
        RoutineNotifications existing = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + id));

        RoutineStep step = routineStepRepository.findById(dto.getRoutineStepId())
                .orElseThrow(() -> new RuntimeException("Routine Step not found with ID: " + dto.getRoutineStepId()));

        existing.setTitle(dto.getTitle());
        existing.setMessage(dto.getMessage());
        existing.setActive(dto.isActive());
        existing.setRoutineStep(step); // Update the Join!

        return mapToDTO(notificationRepository.save(existing));
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete: Notification not found");
        }
        notificationRepository.deleteById(id);
    }

    /**
     * Helper Method: Converts the Entity back to a DTO,
     * safely extracting the joined RoutineStep data.
     */
    private RoutineNotificationsDTO mapToDTO(RoutineNotifications entity) {
        return RoutineNotificationsDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .isActive(entity.isActive())
                // Extracting the Joined Data automatically
                .routineStepId(entity.getRoutineStep() != null ? entity.getRoutineStep().getId() : null)
                .routineStepName(entity.getRoutineStep() != null ? entity.getRoutineStep().getProductName() : "Unknown")
                .scheduledTime(entity.getRoutineStep() != null ? entity.getRoutineStep().getScheduledTime() : "Unknown")
                .pathCategory(entity.getRoutineStep() != null ? entity.getRoutineStep().getPathCategory() : "Unknown")
                .build();
    }
}