package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.cli.commands.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class CommandFactory {
    private static final AutomatonManager manager = AutomatonManager.getInstance();
    private final Map<CommandType, Command> commandCreators;
    private static CommandFactory instance;

    private CommandFactory() {
        this.commandCreators = new EnumMap<>(CommandType.class);
        registerCommands();
    }

    private void registerCommands() {
        commandCreators.put(CommandType.HELP, new HelpCommand());
        commandCreators.put(CommandType.EXIT, new ExitCommand());
        commandCreators.put(CommandType.OPEN, new OpenCommand(manager));
        commandCreators.put(CommandType.CLOSE, new CloseCommand(manager));
        commandCreators.put(CommandType.LIST, new ListCommand(manager));
        commandCreators.put(CommandType.PRINT, new PrintCommand(manager));
        commandCreators.put(CommandType.SAVE, new SaveCommand(manager));
        commandCreators.put(CommandType.SAVEAS, new SaveAsCommand(manager));
        commandCreators.put(CommandType.RECOGNIZE, new RecognizeCommand(manager));
        commandCreators.put(CommandType.DETERMINISTIC, new DeterministicCommand(manager));
        commandCreators.put(CommandType.DETERMINIZE, new DeterminizeCommand(manager));
        commandCreators.put(CommandType.EMPTY, new EmptyCommand(manager));
        commandCreators.put(CommandType.UNION, new UnionCommand(manager));
        commandCreators.put(CommandType.CONCAT, new ConcatCommand(manager));
        commandCreators.put(CommandType.STAR, new StarCommand(manager));
        commandCreators.put(CommandType.SELECT, new SelectCommand(manager));
        commandCreators.put(CommandType.REG, new RegexCommand(manager));
    }

    public static CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    public Optional<Command> getCommand(CommandType type) {
        return Optional.ofNullable(commandCreators.get(type));
    }
}
