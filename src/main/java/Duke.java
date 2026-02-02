import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static class Task {
        String name;
        boolean done;

        Task(String name, boolean done) {
            this.name = name;
            this.done = done;
        }
    }
    public static void main(String[] args) {
        String name = "fzjjs";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        System.out.println(line);
        System.out.println(" Hello! I'm " + name);
        System.out.println(" What can I do for you?");
        System.out.println(line);

        ArrayList<Task> ls = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();
            System.out.println(line);
            if (input.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < ls.size(); i++) {
                    System.out.println(i+1 + ". " + ls.get(i));
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5));
                ls.get(index-1).done = true;
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7));
                ls.get(index-1).done = false;
            }
            else {
                ls.add(new Task(input, false));
                System.out.println("added: " + input);
            }
        }

        scanner.close();
    }
}
