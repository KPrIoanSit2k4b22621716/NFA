package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFADeterminism;

public class DeterministicCommand implements Command {
    private final AutomatonManager manager;

    public DeterministicCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        System.out.println("Автоматът е " + (NFADeterminism.isDeterministic(nfa) ? "ДЕТЕРМИНИРАН" : "НЕДЕТЕРМИНИРАН"));
    }
}