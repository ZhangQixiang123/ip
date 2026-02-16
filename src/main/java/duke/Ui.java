package duke;

import java.util.Scanner;

/**
 * Handles console interactions with the user (reading input and printing output).
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

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void close() {
        scanner.close();
    }
}
