package lk.ijse.glowcare_backend.controller;

import lk.ijse.glowcare_backend.dto.RoutineNotificationsDTO;
import lk.ijse.glowcare_backend.service.RoutineNotificationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routine-notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RoutineNotificationsController {

    private final RoutineNotificationsService notificationService;

    // 1. Public: View active notifications (used by the RoutineHub banner)
    @GetMapping("/active")
    public ResponseEntity<List<RoutineNotificationsDTO>> getActiveNotifications() {
        return ResponseEntity.ok(notificationService.getActiveNotifications());
    }

    // 2. Admin: View all (active + inactive) for the Dashboard
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoutineNotificationsDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // 3. Admin: Create a new notification
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoutineNotificationsDTO> createNotification(@RequestBody RoutineNotificationsDTO dto) {
        RoutineNotificationsDTO createdNotification = notificationService.createNotification(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    // 4. Admin: Update an existing notification
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoutineNotificationsDTO> updateNotification(
            @PathVariable Long id,
            @RequestBody RoutineNotificationsDTO dto) {

        RoutineNotificationsDTO updatedNotification = notificationService.updateNotification(id, dto);
        return ResponseEntity.ok(updatedNotification);
    }

    // 5. Admin: Delete a notification
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}