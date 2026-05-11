package bg.tu_varna.sit.cli;

import java.util.Scanner;

public class CLI {
    private final AutomatonManager manager = new AutomatonManager();
    private final CommandFactory factory = new CommandFactory(manager);

    public void start() {
        System.out.println("=== NFA Command Line Interface ===");
        System.out.println("Type 'HELP' for available commands\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();


            input = input == null ? "" : input.trim();
            if (input.isEmpty()) {
                continue;
            }

            CommandParser.Command parsedCmd = CommandParser.parse(input);

            if (parsedCmd.getName().isEmpty()) {
                continue;
            }

            Command command = factory.createCommand(parsedCmd.getName(), parsedCmd.getArgs());

            if (command != null) {
                try {
                    command.execute();
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                    System.out.println("Please check your input and try again.");
                }
            }
        }
    }
}