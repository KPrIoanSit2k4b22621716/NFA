package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.RegexToNFA;

import java.util.List;

public class RegexCommand implements Command {
    private final AutomatonManager manager;

    public RegexCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        String regex = CommandArguments.requireSingleArgument(args, "reg <regularExpression>");
        if (regex == null) {
            return;
        }

        try {
            NFA nfa = RegexToNFA.fromRegex(regex);
            int newId = manager.addAutomaton(nfa);
            System.out.println("Автоматът от регулярен израз е създаден с ID: " + newId);
        } catch (Exception e) {
            System.out.println("Грешка при парсване на regex: " + e.getMessage());
        }
    }
}
