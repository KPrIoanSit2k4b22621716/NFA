package bg.tu_varna.sit.cli;

public enum CommandType {
    HELP("help", "Displays this help message"),
    EXIT("exit", "Exits the program"),
    OPEN("open", "Opens or creates an automaton from a file"),
    CLOSE("close", "Closes the currently open automaton"),
    LIST("list", "Lists all loaded automata with their IDs"),
    PRINT("print", "Prints all transitions of the current automaton"),
    SAVE("save", "Saves the current automaton to the opened file"),
    SAVEAS("saveas", "Saves the current automaton to a new file"),
    RECOGNIZE("recognize", "Checks if a word is accepted by the automaton"),
    DETERMINISTIC("deterministic", "Checks if the automaton is deterministic"),
    DETERMINIZE("determinize", "Converts NFA to DFA (subset construction)"),
    EMPTY("empty", "Checks if the language of the automaton is empty"),
    UNION("union", "Creates union of current automaton with another automaton"),
    CONCAT("concat", "Creates concatenation of current automaton with another automaton"),
    STAR("star", "Creates Kleene star of the current automaton"),
    SELECT("select", "Switches current automaton to the specified ID"),
    REG("reg", "Creates an automaton from a regular expression");

    private final String command;
    private final String description;

    CommandType(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static CommandType fromString(String text) {
        for (CommandType cmd : CommandType.values()) {
            if (cmd.command.equalsIgnoreCase(text)) {
                return cmd;
            }
        }
        return null;
    }
}