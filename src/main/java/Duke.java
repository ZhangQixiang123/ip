import java.util.ArrayList;
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

        ArrayList<String> ls = new ArrayList<>();

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
            } else {
                ls.add(input);
                System.out.println("added: " + input);
            }
        }

        scanner.close();
    }
}
