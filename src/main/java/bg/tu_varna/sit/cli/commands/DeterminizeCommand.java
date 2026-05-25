package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFADeterminism;

import java.util.List;

public class DeterminizeCommand implements Command {
    private final AutomatonManager manager;

    public DeterminizeCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        if (!CommandArguments.requireNoArgs(args, "determinize")) {
            return;
        }
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
