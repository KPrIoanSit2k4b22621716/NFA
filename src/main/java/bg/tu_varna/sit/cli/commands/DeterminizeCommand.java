package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFADeterminism;

public class DeterminizeCommand implements Command {
    private final AutomatonManager manager;

    public DeterminizeCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        NFA dfa = NFADeterminism.determinize(nfa);
        int newId = manager.addAutomaton(dfa);
        System.out.println("Детерминираният автомат е създаден с ID: " + newId);
    }
}