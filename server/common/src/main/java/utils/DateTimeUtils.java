package utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final DateTimeFormatter HUMAN_DATE_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy");
    public static final DateTimeFormatter HUMAN_TIME_FORMAT =
            DateTimeFormatter.ofPattern("h:mm a");
    public static final DateTimeFormatter HUMAN_FULL_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:mm a");

    public static LocalDateTime getStartOfDay(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toLocalDate().atStartOfDay();
    }

    public static LocalDateTime getEndOfDay(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toLocalDate().atTime(LocalTime.MAX);
    }

    public static String formatFriendlyDate(LocalDateTime dateTime) {
        return dateTime.format(HUMAN_DATE_FORMAT);
    }
    
    public static String formatFriendlyFull(LocalDateTime dateTime) {
        return dateTime.format(HUMAN_FULL_FORMAT);
    }
}