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

    public Duke() {
        this(DATA_FILE_PATH);
    }

    public Duke(String filePath) {
        storage = new Storage(filePath);
        try {
            ArrayList<Task> loadedTasks = storage.load();
            tasks = new TaskList(loadedTasks);
        } catch (IOException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            return Parser.processCommand(input, tasks, storage);
        } catch (DukeException e) {
            return "OOPS!!! " + e.getMessage();
        }
    }

    public void run() {
        Ui ui = new Ui();
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            String input = ui.readCommand();
            ui.showLine();
            String response = getResponse(input);
            System.out.println(" " + response);
            if (input.trim().equals("bye")) {
                ui.showLine();
                break;
            }
            ui.showLine();
        }
        ui.close();
    }

    public static void main(String[] args) {
        new Duke(DATA_FILE_PATH).run();
    }
}
