package lk.ijse.glowcare_backend.util;

import lk.ijse.glowcare_backend.dto.AdminDashboardDTO;
import java.time.LocalDateTime;
import java.time.Duration;

/**
 * AdminActivityMapper
 * Utility class to format raw system events into
 * human-readable "Smart" strings for the Nexus Dashboard.
 */
public class AdminActivityMapper {

    /**
     * Formats a LocalDateTime into a "Smart" timestamp string.
     * Example: "Just Now", "5m ago", "1h ago"
     */
    public static String mapToRelativeTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";

        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        long seconds = duration.getSeconds();

        if (seconds < 60) return "Just Now";
        if (seconds < 3600) return (seconds / 60) + "m ago";
        if (seconds < 86400) return (seconds / 3600) + "h ago";

        return "Yesterday";
    }

    /**
     * Helper to create a standardized Activity DTO.
     * Ensures the status is always uppercase to match React logic.
     */
    public static AdminDashboardDTO.RecentActivityDTO createActivity(String title, String time, String status) {
        return AdminDashboardDTO.RecentActivityDTO.builder()
                .title(title) // Changed from .action() to .title()
                .timestamp(time)
                .status(status.toUpperCase())
                .build();
    }
}