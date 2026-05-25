package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFAOperations;

import java.util.List;

public class StarCommand implements Command {
    private final AutomatonManager manager;

    public StarCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        if (!CommandArguments.requireNoArgs(args, "star")) {
            return;
        }
        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        NFA result = NFAOperations.star(current);
        int newId = manager.addAutomaton(result);
        System.out.println("Звездата на автомата е създадена с ID: " + newId);
    }
}
