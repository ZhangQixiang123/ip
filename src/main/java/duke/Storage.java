package duke;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * Handles loading and saving tasks to a file on disk.
 */
public class Storage {
    private static final String FILE_DELIMITER = " \\| ";
    private String filePath;

    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.trim().isEmpty() : "File path should not be empty";
        this.filePath = filePath;
    }

    /**
     * Saves all tasks to the file, creating the directory if needed.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        assert tasks != null : "Task list to save should not be null";
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        }
    }

    /**
     * Loads tasks from the file. Returns an empty list if the file does not exist.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTaskFromFile(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    private Task parseTaskFromFile(String line) {
        assert line != null : "Line to parse should not be null";
        try {
            String[] parts = line.split(FILE_DELIMITER);
            assert parts.length >= 3 : "File line should have at least 3 parts (type, done, description)";
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = createTaskFromType(type, description, parts);
            if (task == null) {
                return null;
            }
            if (isDone) {
                task.markAsDone();
            }
            return task;
        } catch (Exception e) {
            System.out.println(" Warning: Skipping corrupted line: " + line);
            return null;
        }
    }

    private Task createTaskFromType(String type, String description, String[] parts) {
        switch (type) {
        case "T":
            return new ToDo(description);
        case "D":
            return new Deadline(description, parts[3]);
        case "E":
            return new Event(description, parts[3], parts[4]);
        default:
            return null;
        }
    }
}
