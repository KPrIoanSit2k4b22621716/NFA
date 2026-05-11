package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFAOperations;

public class EmptyCommand implements Command {
    private final AutomatonManager manager;

    public EmptyCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        System.out.println("Езикът на автомата е " + (NFAOperations.isEmptyLanguage(nfa) ? "ПРАЗЕН" : "НЕ Е ПРАЗЕН"));
    }
}