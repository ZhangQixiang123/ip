import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String name = "fzjjs";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm " + name);
        System.out.println(" What can I do for you?");
        System.out.println(line);

        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);
            if (input.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            System.out.println(" " + input);
            System.out.println(line);
        }

        scanner.close();
    }
}
