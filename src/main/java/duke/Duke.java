package duke;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import duke.task.Task;

/**
 * Duke is a task management chatbot that supports ToDos, Deadlines, and Events.
 */
public class Duke {
    private static final String DATA_FILE_PATH = "data" + File.separator + "duke.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (IOException e) {
            ui.showWarning("Could not load tasks from file.");
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String input = ui.readCommand();
            ui.showLine();
            isRunning = Parser.executeCommand(input, tasks, ui, storage);
            ui.showLine();
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Duke(DATA_FILE_PATH).run();
    }
}
