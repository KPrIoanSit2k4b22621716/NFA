package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.RegexToNFA;

public class RegexCommand implements Command {
    private final AutomatonManager manager;
    private final String regex;

    public RegexCommand(AutomatonManager manager, String regex) {
        this.manager = manager;
        this.regex = regex;
    }

    @Override
    public void execute() {
        try {
            NFA nfa = RegexToNFA.fromRegex(regex);
            int newId = manager.addAutomaton(nfa);
            System.out.println("Автоматът от регулярен израз е създаден с ID: " + newId);
        } catch (Exception e) {
            System.out.println("Грешка при парсване на regex: " + e.getMessage());
        }
    }
}