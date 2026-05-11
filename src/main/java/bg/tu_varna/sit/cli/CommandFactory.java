package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.cli.commands.*;
import java.util.List;

public class CommandFactory {
    private final AutomatonManager manager;

    public CommandFactory(AutomatonManager manager) {
        this.manager = manager;
    }

    public Command createCommand(String name, List<String> args) {
        return switch (name) {
            case "help" -> new HelpCommand();
            case "exit" -> new ExitCommand();
            case "open" -> {
                if (args.isEmpty()) {
                    yield null;
                }
                yield new OpenCommand(manager, args.get(0));
            }
            case "close" -> new CloseCommand(manager);
            case "list" -> new ListCommand(manager);
            case "print" -> new PrintCommand(manager);
            case "save" -> new SaveCommand(manager);
            case "saveas" -> {
                if (args.isEmpty()) {
                    yield null;
                }
                yield new SaveAsCommand(manager, args.get(0));
            }
            case "recognize" -> {
                if (args.isEmpty()) {
                    yield null;
                }
                yield new RecognizeCommand(manager, args.get(0));
            }
            case "deterministic" -> new DeterministicCommand(manager);
            case "determinize" -> new DeterminizeCommand(manager);
            case "empty" -> new EmptyCommand(manager);
            case "union" -> {
                if (args.isEmpty()) {
                    System.out.println("Грешка: union <id> - липсва ID");
                    yield null;
                }
                try {
                    int id = Integer.parseInt(args.get(0));
                    yield new UnionCommand(manager, id);
                } catch (NumberFormatException e) {
                    System.out.println("Грешка: ID трябва да е число");
                    yield null;
                }
            }
            case "concat" -> {
                if (args.isEmpty()) {
                    System.out.println("Грешка: concat <id> - липсва ID");
                    yield null;
                }
                try {
                    int id = Integer.parseInt(args.get(0));
                    yield new ConcatCommand(manager, id);
                } catch (NumberFormatException e) {
                    System.out.println("Грешка: ID трябва да е число");
                    yield null;
                }
            }
            case "star" -> new StarCommand(manager);
            case "select" -> {
                if (args.isEmpty()) {
                    System.out.println("Error: SELECT <id> - automaton ID required");
                    yield null;
                }
                try {
                    int id = Integer.parseInt(args.get(0));
                    if (manager.getAutomatonById(id) == null) {
                        System.out.println("Error: No automaton with ID " + id);
                        yield null;
                    }
                    yield new SelectCommand(manager, id);
                } catch (NumberFormatException e) {
                    System.out.println("Error: SELECT <id> - ID must be a number");
                    yield null;
                }
            }
            case "reg" -> {
                if (args.isEmpty()) {
                    System.out.println("Грешка: reg <regex> - липсва регулярен израз");
                    yield null;
                }
                yield new RegexCommand(manager, args.get(0));
            }
            default -> {
                System.out.println("Неизвестна команда: " + name);
                yield null;
            }
        };
    }
}