package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * Parses user input and executes the corresponding command.
 */
public class Parser {

    /**
     * Parses and executes a single user command.
     * Returns false if the program should exit, true otherwise.
     */
    public static boolean executeCommand(String input, TaskList tasks, Ui ui, Storage storage) {
        String command = getCommandWord(input);
        String arguments = getArguments(input);

        switch (command) {
        case "bye":
            ui.showGoodbye();
            return false;
        case "list":
            ui.showTaskList(tasks);
            break;
        case "mark":
            handleMark(arguments, tasks, ui, storage);
            break;
        case "unmark":
            handleUnmark(arguments, tasks, ui, storage);
            break;
        case "delete":
            handleDelete(arguments, tasks, ui, storage);
            break;
        case "todo":
            handleToDo(arguments, tasks, ui, storage);
            break;
        case "deadline":
            handleDeadline(arguments, tasks, ui, storage);
            break;
        case "event":
            handleEvent(arguments, tasks, ui, storage);
            break;
        default:
            ui.showError("I don't know what that means :-(");
            System.out.println(" Try: todo, deadline, event, list, mark, unmark, delete, bye");
            break;
        }
        return true;
    }

    private static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    private static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    private static void handleMark(String arguments, TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = parseTaskIndex(arguments);
            tasks.markTask(index);
            saveTasks(tasks, storage, ui);
            ui.showTaskMarked(tasks.getTask(index));
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void handleUnmark(String arguments, TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = parseTaskIndex(arguments);
            tasks.unmarkTask(index);
            saveTasks(tasks, storage, ui);
            ui.showTaskUnmarked(tasks.getTask(index));
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void handleDelete(String arguments, TaskList tasks, Ui ui, Storage storage) {
        try {
            int index = parseTaskIndex(arguments);
            Task removed = tasks.deleteTask(index);
            saveTasks(tasks, storage, ui);
            ui.showTaskDeleted(removed, tasks.size());
        } catch (DukeException e) {
            ui.showError(e.getMessage());
        }
    }

    private static void handleToDo(String arguments, TaskList tasks, Ui ui, Storage storage) {
        if (arguments.isEmpty()) {
            ui.showError("The description of a todo cannot be empty.");
            return;
        }
        Task task = new ToDo(arguments);
        tasks.addTask(task);
        saveTasks(tasks, storage, ui);
        ui.showTaskAdded(task, tasks.size());
    }

    private static void handleDeadline(String arguments, TaskList tasks, Ui ui, Storage storage) {
        if (arguments.isEmpty()) {
            ui.showError("The description of a deadline cannot be empty.");
            return;
        }
        int byIndex = arguments.indexOf(" /by ");
        if (byIndex == -1) {
            ui.showError("Please specify a deadline using /by.");
            System.out.println(" Usage: deadline <description> /by <date>");
            return;
        }
        String description = arguments.substring(0, byIndex).trim();
        String by = arguments.substring(byIndex + " /by ".length()).trim();
        if (description.isEmpty()) {
            ui.showError("The description of a deadline cannot be empty.");
            return;
        }
        if (by.isEmpty()) {
            ui.showError("The deadline date cannot be empty.");
            return;
        }
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        saveTasks(tasks, storage, ui);
        ui.showTaskAdded(task, tasks.size());
    }

    private static void handleEvent(String arguments, TaskList tasks, Ui ui, Storage storage) {
        if (arguments.isEmpty()) {
            ui.showError("The description of an event cannot be empty.");
            return;
        }
        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1) {
            ui.showError("Please specify event times using /from and /to.");
            System.out.println(" Usage: event <description> /from <start> /to <end>");
            return;
        }
        if (fromIndex > toIndex) {
            ui.showError("Please put /from before /to.");
            return;
        }
        String description = arguments.substring(0, fromIndex).trim();
        String from = arguments.substring(fromIndex + " /from ".length(), toIndex).trim();
        String to = arguments.substring(toIndex + " /to ".length()).trim();
        if (description.isEmpty()) {
            ui.showError("The description of an event cannot be empty.");
            return;
        }
        if (from.isEmpty()) {
            ui.showError("The start time cannot be empty.");
            return;
        }
        if (to.isEmpty()) {
            ui.showError("The end time cannot be empty.");
            return;
        }
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        saveTasks(tasks, storage, ui);
        ui.showTaskAdded(task, tasks.size());
    }

    private static int parseTaskIndex(String arguments) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("Please specify a task number.");
        }
        try {
            return Integer.parseInt(arguments) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a valid task number.");
        }
    }

    private static void saveTasks(TaskList tasks, Storage storage, Ui ui) {
        try {
            storage.save(tasks.getAllTasks());
        } catch (Exception e) {
            ui.showWarning("Could not save tasks to file.");
        }
    }
}
