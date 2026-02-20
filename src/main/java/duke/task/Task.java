package duke.task;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        assert description != null : "Task description should not be null";
        assert !description.trim().isEmpty() : "Task description should not be empty";
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        this.isDone = true;
        assert isDone : "Task should be done after markAsDone";
    }

    public void markAsNotDone() {
        this.isDone = false;
        assert !isDone : "Task should not be done after markAsNotDone";
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns a formatted string for saving this task to a file.
     */
    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
