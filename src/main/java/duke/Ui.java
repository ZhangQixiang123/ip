package duke;

import java.util.Scanner;

import duke.task.Task;

/**
 * Handles all interactions with the user (reading input and printing output).
 */
public class Ui {
    private static final String SEPARATOR =
            "____________________________________________________________";
    private static final String BOT_NAME = "fzjjs";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm " + BOT_NAME);
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void showError(String message) {
        System.out.println(" OOPS!!! " + message);
    }

    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                System.out.println(" " + (i + 1) + "." + tasks.getTask(i));
            } catch (DukeException e) {
                // Should not happen during iteration
            }
        }
    }

    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + totalTasks + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }

    public void showWarning(String message) {
        System.out.println(" Warning: " + message);
    }

    public void close() {
        scanner.close();
    }
}
