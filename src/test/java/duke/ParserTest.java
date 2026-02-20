package duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParserTest {
    private TaskList tasks;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
        storage = new Storage("data/test_duke.txt");
    }

    @Test
    public void processCommand_todo_addsTask() throws DukeException {
        String response = Parser.processCommand("todo read book", tasks, storage);
        assertEquals(1, tasks.size());
        assertTrue(response.contains("read book"));
    }

    @Test
    public void processCommand_emptyTodo_throwsException() {
        assertThrows(DukeException.class, () ->
                Parser.processCommand("todo", tasks, storage));
    }

    @Test
    public void processCommand_unknownCommand_throwsException() {
        assertThrows(DukeException.class, () ->
                Parser.processCommand("blah", tasks, storage));
    }

    @Test
    public void processCommand_deadline_addsTask() throws DukeException {
        String response = Parser.processCommand("deadline return book /by 2024-01-15", tasks, storage);
        assertEquals(1, tasks.size());
        assertTrue(response.contains("return book"));
    }

    @Test
    public void processCommand_deadlineMissingBy_throwsException() {
        assertThrows(DukeException.class, () ->
                Parser.processCommand("deadline return book", tasks, storage));
    }

    @Test
    public void processCommand_find_findsMatchingTasks() throws DukeException {
        Parser.processCommand("todo read book", tasks, storage);
        Parser.processCommand("todo write essay", tasks, storage);
        Parser.processCommand("todo read notes", tasks, storage);
        String response = Parser.processCommand("find read", tasks, storage);
        assertTrue(response.contains("read book"));
        assertTrue(response.contains("read notes"));
    }

    @Test
    public void processCommand_find_noMatch() throws DukeException {
        Parser.processCommand("todo read book", tasks, storage);
        String response = Parser.processCommand("find xyz", tasks, storage);
        assertEquals("No matching tasks found.", response);
    }
}
