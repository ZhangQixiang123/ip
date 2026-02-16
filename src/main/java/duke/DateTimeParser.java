package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing date/time strings in various formats.
 */
public class DateTimeParser {
    private static final String[] FORMATS_WITH_TIME = {"d/M/yyyy HHmm", "yyyy-MM-dd HHmm"};
    private static final String[] FORMATS_DATE_ONLY = {"d/M/yyyy", "yyyy-MM-dd"};

    /**
     * Parses a date/time string, trying multiple known formats.
     * Returns null if no format matches (caller should use the raw string as fallback).
     */
    public static LocalDateTime parse(String input) {
        LocalDateTime result = tryParseWithTime(input);
        if (result != null) {
            return result;
        }
        return tryParseDateOnly(input);
    }

    private static LocalDateTime tryParseWithTime(String input) {
        for (String pattern : FORMATS_WITH_TIME) {
            try {
                return LocalDateTime.parse(input, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }
        return null;
    }

    private static LocalDateTime tryParseDateOnly(String input) {
        for (String pattern : FORMATS_DATE_ONLY) {
            try {
                return LocalDateTime.parse(input + " 0000",
                        DateTimeFormatter.ofPattern(pattern + " HHmm"));
            } catch (DateTimeParseException e) {
                // Try next format
            }
        }
        return null;
    }
}
