package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.DateTimeParser;

/**
 * Represents a task that spans a time period (from start to end).
 */
public class Event extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String startString;
    private String endString;

    public Event(String description, String from, String to) {
        super(description);
        this.startDateTime = DateTimeParser.parse(from);
        this.endDateTime = DateTimeParser.parse(to);
        this.startString = from;
        this.endString = to;
    }

    @Override
    public String toFileString() {
        String formattedStart = startDateTime != null
                ? startDateTime.format(FILE_FORMAT)
                : startString;
        String formattedEnd = endDateTime != null
                ? endDateTime.format(FILE_FORMAT)
                : endString;
        return "E | " + super.toFileString() + " | " + formattedStart + " | " + formattedEnd;
    }

    @Override
    public String toString() {
        String formattedStart = startDateTime != null
                ? startDateTime.format(OUTPUT_FORMAT)
                : startString;
        String formattedEnd = endDateTime != null
                ? endDateTime.format(OUTPUT_FORMAT)
                : endString;
        return "[E]" + super.toString() + " (from: " + formattedStart + " to: " + formattedEnd + ")";
    }
}
