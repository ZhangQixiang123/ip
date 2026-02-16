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
     * Parses and executes a single user command, returning the response as a string.
     */
    public static String processCommand(String input, TaskList tasks, Storage storage) throws DukeException {
        String command = getCommandWord(input);
        String arguments = getArguments(input);

        switch (command) {
        case "bye":
            return "Bye. Hope to see you again soon!";
        case "list":
            return formatTaskList(tasks);
        case "mark":
            return handleMark(arguments, tasks, storage);
        case "unmark":
            return handleUnmark(arguments, tasks, storage);
        case "delete":
            return handleDelete(arguments, tasks, storage);
        case "todo":
            return handleToDo(arguments, tasks, storage);
        case "deadline":
            return handleDeadline(arguments, tasks, storage);
        case "event":
            return handleEvent(arguments, tasks, storage);
        default:
            throw new DukeException("I don't know what that means :-(\n"
                    + " Try: todo, deadline, event, list, mark, unmark, delete, bye");
        }
    }

    private static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    private static String getArguments(String input) {
        String[] parts = input.split(" ", 2);
        return parts.length > 1 ? parts[1].trim() : "";
    }

    private static String formatTaskList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                sb.append("\n ").append(i + 1).append(".").append(tasks.getTask(i));
            } catch (DukeException e) {
                // Should not happen during iteration
            }
        }
        return sb.toString();
    }

    private static String formatTaskAdded(Task task, int totalTasks) {
        return "Got it. I've added this task:\n   " + task
                + "\n Now you have " + totalTasks + " tasks in the list.";
    }

    private static String handleMark(String arguments, TaskList tasks, Storage storage) throws DukeException {
        int index = parseTaskIndex(arguments);
        tasks.markTask(index);
        saveTasks(tasks, storage);
        return "Nice! I've marked this task as done:\n   " + tasks.getTask(index);
    }

    private static String handleUnmark(String arguments, TaskList tasks, Storage storage) throws DukeException {
        int index = parseTaskIndex(arguments);
        tasks.unmarkTask(index);
        saveTasks(tasks, storage);
        return "OK, I've marked this task as not done yet:\n   " + tasks.getTask(index);
    }

    private static String handleDelete(String arguments, TaskList tasks, Storage storage) throws DukeException {
        int index = parseTaskIndex(arguments);
        Task removed = tasks.deleteTask(index);
        saveTasks(tasks, storage);
        return "Noted. I've removed this task:\n   " + removed
                + "\n Now you have " + tasks.size() + " tasks in the list.";
    }

    private static String handleToDo(String arguments, TaskList tasks, Storage storage) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        Task task = new ToDo(arguments);
        tasks.addTask(task);
        saveTasks(tasks, storage);
        return formatTaskAdded(task, tasks.size());
    }

    private static String handleDeadline(String arguments, TaskList tasks, Storage storage) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        int byIndex = arguments.indexOf(" /by ");
        if (byIndex == -1) {
            throw new DukeException("Please specify a deadline using /by.\n"
                    + " Usage: deadline <description> /by <date>");
        }
        String description = arguments.substring(0, byIndex).trim();
        String by = arguments.substring(byIndex + " /by ".length()).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new DukeException("The deadline date cannot be empty.");
        }
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        saveTasks(tasks, storage);
        return formatTaskAdded(task, tasks.size());
    }

    private static String handleEvent(String arguments, TaskList tasks, Storage storage) throws DukeException {
        if (arguments.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        int fromIndex = arguments.indexOf(" /from ");
        int toIndex = arguments.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1) {
            throw new DukeException("Please specify event times using /from and /to.\n"
                    + " Usage: event <description> /from <start> /to <end>");
        }
        if (fromIndex > toIndex) {
            throw new DukeException("Please put /from before /to.");
        }
        String description = arguments.substring(0, fromIndex).trim();
        String from = arguments.substring(fromIndex + " /from ".length(), toIndex).trim();
        String to = arguments.substring(toIndex + " /to ".length()).trim();
        if (description.isEmpty()) {
            throw new DukeException("The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new DukeException("The start time cannot be empty.");
        }
        if (to.isEmpty()) {
            throw new DukeException("The end time cannot be empty.");
        }
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        saveTasks(tasks, storage);
        return formatTaskAdded(task, tasks.size());
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

    private static void saveTasks(TaskList tasks, Storage storage) throws DukeException {
        try {
            storage.save(tasks.getAllTasks());
        } catch (Exception e) {
            throw new DukeException("Could not save tasks to file.");
        }
    }
}
