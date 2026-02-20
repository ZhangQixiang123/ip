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
        assert task != null : "Task to add should not be null";
        int previousSize = tasks.size();
        tasks.add(task);
        assert tasks.size() == previousSize + 1 : "Task list size should increase by 1 after add";
    }

    public Task deleteTask(int index) throws DukeException {
        validateIndex(index);
        int previousSize = tasks.size();
        Task removed = tasks.remove(index);
        assert removed != null : "Removed task should not be null";
        assert tasks.size() == previousSize - 1 : "Task list size should decrease by 1 after delete";
        return removed;
    }

    public void markTask(int index) throws DukeException {
        validateIndex(index);
        tasks.get(index).markAsDone();
        assert tasks.get(index).isDone() : "Task should be done after marking";
    }

    public void unmarkTask(int index) throws DukeException {
        validateIndex(index);
        tasks.get(index).markAsNotDone();
        assert !tasks.get(index).isDone() : "Task should not be done after unmarking";
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
