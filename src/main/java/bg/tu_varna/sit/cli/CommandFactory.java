package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.cli.commands.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandFactory {
    private final AutomatonManager manager;
    private final Map<String, Function<List<String>, Command>> commandCreators;

    public CommandFactory(AutomatonManager manager) {
        this.manager = manager;
        this.commandCreators = new HashMap<>();
        commandCreators.put("help", args -> new HelpCommand());
        commandCreators.put("exit", args -> new ExitCommand());
        commandCreators.put("close", args -> new CloseCommand(manager));
        commandCreators.put("list", args -> new ListCommand(manager));
        commandCreators.put("print", args -> new PrintCommand(manager));
        commandCreators.put("save", args -> new SaveCommand(manager));
        commandCreators.put("star", args -> new StarCommand(manager));
        commandCreators.put("deterministic", args -> new DeterministicCommand(manager));
        commandCreators.put("determinize", args -> new DeterminizeCommand(manager));
        commandCreators.put("empty", args -> new EmptyCommand(manager));

        commandCreators.put("open", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: open <file> - липсва път към файл");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: open приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            return new OpenCommand(manager, args.get(0));
        });

        commandCreators.put("saveas", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: saveas <file> - липсва път към файл");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: saveas приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            return new SaveAsCommand(manager, args.get(0));
        });

        commandCreators.put("recognize", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: recognize <word> - липсва дума");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: recognize приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            return new RecognizeCommand(manager, args.get(0));
        });

        commandCreators.put("reg", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: reg <regex> - липсва регулярен израз");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: reg приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            return new RegexCommand(manager, args.get(0));
        });

        commandCreators.put("select", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: select <id> - липсва ID");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: select приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            try {
                int id = Integer.parseInt(args.get(0));
                if (manager.getAutomatonById(id) == null) {
                    System.out.println("Грешка: Няма автомат с ID " + id);
                    return null;
                }
                return new SelectCommand(manager, id);
            } catch (NumberFormatException e) {
                System.out.println("Грешка: ID трябва да е число");
                return null;
            }
        });

        commandCreators.put("union", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: union <id> - липсва ID");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: union приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            try {
                int id = Integer.parseInt(args.get(0));
                return new UnionCommand(manager, id);
            } catch (NumberFormatException e) {
                System.out.println("Грешка: ID трябва да е число");
                return null;
            }
        });

        commandCreators.put("concat", args -> {
            if (args.isEmpty()) {
                System.out.println("Грешка: concat <id> - липсва ID");
                return null;
            }
            if (args.size() > 1) {
                System.out.println("Грешка: concat приема само 1 аргумент, а Вие подадохте " + args.size());
                return null;
            }
            try {
                int id = Integer.parseInt(args.get(0));
                return new ConcatCommand(manager, id);
            } catch (NumberFormatException e) {
                System.out.println("Грешка: ID трябва да е число");
                return null;
            }
        });
    }

    public Command createCommand(String name, List<String> args) {
        Function<List<String>, Command> creator = commandCreators.get(name);
        if (creator == null) {
            System.out.println("Неизвестна команда: " + name);
            return null;
        }
        return creator.apply(args);
    }
}