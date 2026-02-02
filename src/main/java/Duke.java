import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = "duke.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + DATA_FILE;
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

        String toFileString() {
            return (done ? "1" : "0") + " | " + name;
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
        String toFileString() {
            return "T | " + super.toFileString();
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
        String toFileString() {
            return "D | " + super.toFileString() + " | " + by;
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
        String toFileString() {
            return "E | " + super.toFileString() + " | " + from + " | " + to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            File dir = new File(DATA_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH));
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(" Warning: Could not save tasks to file.");
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(" Warning: Could not load tasks from file.");
        }
        return tasks;
    }

    private static Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String name = parts[2];

            Task task;
            switch (type) {
            case "T":
                task = new ToDo(name);
                break;
            case "D":
                task = new Deadline(name, parts[3]);
                break;
            case "E":
                task = new Event(name, parts[3], parts[4]);
                break;
            default:
                return null;
            }
            task.done = isDone;
            return task;
        } catch (Exception e) {
            System.out.println(" Warning: Skipping corrupted line: " + line);
            return null;
        }
    }

    public static void main(String[] args) {
        String name = "fzjjs";
        String line = "____________________________________________________________";
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> ls = loadTasks();

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
            } else if (input.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < ls.size(); i++) {
                    System.out.println(" " + (i + 1) + "." + ls.get(i));
                }
            } else if (input.equals("mark") || input.equals("unmark") || input.equals("delete")) {
                System.out.println(" OOPS!!! Please specify a task number.");
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.substring(5)) - 1;
                    if (index < 0 || index >= ls.size()) {
                        System.out.println(" OOPS!!! Task number " + (index + 1) + " does not exist.");
                    } else {
                        ls.get(index).done = true;
                        saveTasks(ls);
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + ls.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" OOPS!!! Please enter a valid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= ls.size()) {
                        System.out.println(" OOPS!!! Task number " + (index + 1) + " does not exist.");
                    } else {
                        ls.get(index).done = false;
                        saveTasks(ls);
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + ls.get(index));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" OOPS!!! Please enter a valid task number.");
                }
            } else if (input.startsWith("delete ")) {
                try {
                    int index = Integer.parseInt(input.substring(7)) - 1;
                    if (index < 0 || index >= ls.size()) {
                        System.out.println(" OOPS!!! Task number " + (index + 1) + " does not exist.");
                    } else {
                        Task removed = ls.remove(index);
                        saveTasks(ls);
                        System.out.println(" Noted. I've removed this task:");
                        System.out.println("   " + removed);
                        System.out.println(" Now you have " + ls.size() + " tasks in the list.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" OOPS!!! Please enter a valid task number.");
                }
            } else if (input.equals("todo")) {
                System.out.println(" OOPS!!! The description of a todo cannot be empty.");
            } else if (input.startsWith("todo ")) {
                String taskName = input.substring(5).trim();
                if (taskName.isEmpty()) {
                    System.out.println(" OOPS!!! The description of a todo cannot be empty.");
                } else {
                    Task task = new ToDo(taskName);
                    ls.add(task);
                    saveTasks(ls);
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + ls.size() + " tasks in the list.");
                }
            } else if (input.equals("deadline")) {
                System.out.println(" OOPS!!! The description of a deadline cannot be empty.");
            } else if (input.startsWith("deadline ")) {
                String rest = input.substring(9);
                int byIndex = rest.indexOf(" /by ");
                if (byIndex == -1) {
                    System.out.println(" OOPS!!! Please specify a deadline using /by.");
                    System.out.println(" Usage: deadline <description> /by <date>");
                } else {
                    String taskName = rest.substring(0, byIndex).trim();
                    String by = rest.substring(byIndex + 5).trim();
                    if (taskName.isEmpty()) {
                        System.out.println(" OOPS!!! The description of a deadline cannot be empty.");
                    } else if (by.isEmpty()) {
                        System.out.println(" OOPS!!! The deadline date cannot be empty.");
                    } else {
                        Task task = new Deadline(taskName, by);
                        ls.add(task);
                        saveTasks(ls);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + task);
                        System.out.println(" Now you have " + ls.size() + " tasks in the list.");
                    }
                }
            } else if (input.equals("event")) {
                System.out.println(" OOPS!!! The description of an event cannot be empty.");
            } else if (input.startsWith("event ")) {
                String rest = input.substring(6);
                int fromIndex = rest.indexOf(" /from ");
                int toIndex = rest.indexOf(" /to ");
                if (fromIndex == -1 || toIndex == -1) {
                    System.out.println(" OOPS!!! Please specify event times using /from and /to.");
                    System.out.println(" Usage: event <description> /from <start> /to <end>");
                } else if (fromIndex > toIndex) {
                    System.out.println(" OOPS!!! Please put /from before /to.");
                } else {
                    String taskName = rest.substring(0, fromIndex).trim();
                    String from = rest.substring(fromIndex + 7, toIndex).trim();
                    String to = rest.substring(toIndex + 5).trim();
                    if (taskName.isEmpty()) {
                        System.out.println(" OOPS!!! The description of an event cannot be empty.");
                    } else if (from.isEmpty()) {
                        System.out.println(" OOPS!!! The start time cannot be empty.");
                    } else if (to.isEmpty()) {
                        System.out.println(" OOPS!!! The end time cannot be empty.");
                    } else {
                        Task task = new Event(taskName, from, to);
                        ls.add(task);
                        saveTasks(ls);
                        System.out.println(" Got it. I've added this task:");
                        System.out.println("   " + task);
                        System.out.println(" Now you have " + ls.size() + " tasks in the list.");
                    }
                }
            } else {
                System.out.println(" OOPS!!! I don't know what that means :-(");
                System.out.println(" Try: todo, deadline, event, list, mark, unmark, delete, bye");
            }
            System.out.println(line);
        }

        scanner.close();
    }
}
