package bg.tu_varna.sit.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CLI {
    private final CommandFactory factory = CommandFactory.getInstance();

    public void start() {
        System.out.println("=== NFA Command Line Interface ===");
        System.out.println("Type 'HELP' for available commands\n");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            List<String> tokens = tokenize(input);

            if (tokens.isEmpty()) {
                continue;
            }

            Optional<CommandType> type = CommandType.fromString(tokens.get(0));
            if (type.isEmpty()) {
                System.out.println("Invalid command");
                continue;
            }

            List<String> args = tokens.subList(1, tokens.size());
            Optional<Command> command = factory.getCommand(type.get());
            if (command.isEmpty()) {
                System.out.println("Command is not implemented yet.");
                continue;
            }

            try {
                command.get().execute(args);
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                System.out.println("Please check your input and try again.");
            }
        }
    }

    private List<String> tokenize(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(input.trim().split("\\s+")));
    }
}
