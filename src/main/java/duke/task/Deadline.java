package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import duke.DateTimeParser;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");
    private static final DateTimeFormatter FILE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    private LocalDateTime deadlineDateTime;
    private String deadlineString;

    public Deadline(String description, String by) {
        super(description);
        this.deadlineDateTime = DateTimeParser.parse(by);
        this.deadlineString = by;
    }

    @Override
    public String toFileString() {
        String formattedDeadline = deadlineDateTime != null
                ? deadlineDateTime.format(FILE_FORMAT)
                : deadlineString;
        return "D | " + super.toFileString() + " | " + formattedDeadline;
    }

    @Override
    public String toString() {
        String formattedDeadline = deadlineDateTime != null
                ? deadlineDateTime.format(OUTPUT_FORMAT)
                : deadlineString;
        return "[D]" + super.toString() + " (by: " + formattedDeadline + ")";
    }
}
