package lk.ijse.glowcare_backend.scheduler;

import lk.ijse.glowcare_backend.entity.RoutineStep;
import lk.ijse.glowcare_backend.repository.RoutineStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoutineAlarmScheduler {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoutineStepRepository routineStepRepository;

    // Triggers exactly at 00 seconds of every minute (e.g., 08:00:00, 08:01:00)
    @Scheduled(cron = "0 * * * * *")
    public void triggerRoutineAlarms() {
        // Format to match your React UI, e.g., "08:00 AM"
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

        // Find routines that match this exact minute
        List<RoutineStep> dueRoutines = routineStepRepository.findAll();

        for (RoutineStep step : dueRoutines) {
            // Check if the scheduled time matches current time
            if (currentTime.equalsIgnoreCase(step.getScheduledTime())) {

                Map<String, String> alarmPayload = new HashMap<>();
                alarmPayload.put("title", step.getProductName());
                alarmPayload.put("time", step.getScheduledTime());
                alarmPayload.put("type", step.getPathCategory() + " PATH - " + step.getTimeOfDay());
                alarmPayload.put("message", "Time To Routine!");

                // Broadcast to the user's private device channel
                messagingTemplate.convertAndSend("/topic/alarms/global", alarmPayload);
                System.out.println("✅ [ALARM FIRED]: " + step.getProductName() + " at " + currentTime);
            }
        }
    }
}