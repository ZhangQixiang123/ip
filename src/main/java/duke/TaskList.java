package duke;

import java.util.ArrayList;

import duke.task.Task;

/**
 * Manages the list of tasks with add, delete, mark, and unmark operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws DukeException {
        validateIndex(index);
        return tasks.remove(index);
    }

    public void markTask(int index) throws DukeException {
        validateIndex(index);
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) throws DukeException {
        validateIndex(index);
        tasks.get(index).markAsNotDone();
    }

    public Task getTask(int index) throws DukeException {
        validateIndex(index);
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    private void validateIndex(int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Task number " + (index + 1) + " does not exist.");
        }
    }
}
