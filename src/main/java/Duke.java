import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static class Task {
        String name;
        boolean done;

        Task(String name) {
            this.name = name;
            this.done = false;
        }

        String getStatusIcon() {
            return done ? "X" : " ";
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + name;
        }
    }

    private static class ToDo extends Task {
        ToDo(String name) {
            super(name);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    private static class Deadline extends Task {
        String by;

        Deadline(String name, String by) {
            super(name);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    private static class Event extends Task {
        String from;
        String to;

        Event(String name, String from, String to) {
            super(name);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
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
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < ls.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + ls.get(i));
                }
            } else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5)) - 1;
                ls.get(index).done = true;
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + ls.get(index));
            } else if (input.startsWith("unmark ")) {
                int index = Integer.parseInt(input.substring(7)) - 1;
                ls.get(index).done = false;
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + ls.get(index));
            } else if (input.startsWith("todo ")) {
                String taskName = input.substring(5);
                Task task = new ToDo(taskName);
                ls.add(task);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + task);
                System.out.println(" Now you have " + ls.size() + " tasks in the list.");
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9);
                int byIndex = rest.indexOf(" /by ");
                String taskName = rest.substring(0, byIndex);
                String by = rest.substring(byIndex + 5);
                Task task = new Deadline(taskName, by);
                ls.add(task);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + task);
                System.out.println(" Now you have " + ls.size() + " tasks in the list.");
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6);
                int fromIndex = rest.indexOf(" /from ");
                int toIndex = rest.indexOf(" /to ");
                String taskName = rest.substring(0, fromIndex);
                String from = rest.substring(fromIndex + 7, toIndex);
                String to = rest.substring(toIndex + 5);
                Task task = new Event(taskName, from, to);
                ls.add(task);
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + task);
                System.out.println(" Now you have " + ls.size() + " tasks in the list.");
            }
            System.out.println(line);
        }

        scanner.close();
    }
}
