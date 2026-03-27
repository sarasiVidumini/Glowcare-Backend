package lk.ijse.glowcare_backend.service;

import lk.ijse.glowcare_backend.dto.RoutineNotificationsDTO;

import java.util.List;

public interface RoutineNotificationsService {

    List<RoutineNotificationsDTO> getAllNotifications();

    List<RoutineNotificationsDTO> getActiveNotifications();

    RoutineNotificationsDTO createNotification(RoutineNotificationsDTO dto);

    RoutineNotificationsDTO updateNotification(Long id, RoutineNotificationsDTO dto);

    void deleteNotification(Long id);
}
