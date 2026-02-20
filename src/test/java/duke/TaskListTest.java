package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import duke.task.ToDo;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void addTask_singleTask_sizeIncreases() {
        taskList.addTask(new ToDo("test"));
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_validIndex_removesTask() throws DukeException {
        taskList.addTask(new ToDo("task1"));
        taskList.addTask(new ToDo("task2"));
        taskList.deleteTask(0);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_invalidIndex_throwsException() {
        assertThrows(DukeException.class, () -> taskList.deleteTask(0));
    }

    @Test
    public void markTask_validIndex_marksAsDone() throws DukeException {
        taskList.addTask(new ToDo("test"));
        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());
    }

    @Test
    public void unmarkTask_validIndex_marksAsNotDone() throws DukeException {
        taskList.addTask(new ToDo("test"));
        taskList.markTask(0);
        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        assertThrows(DukeException.class, () -> taskList.getTask(5));
    }
}
