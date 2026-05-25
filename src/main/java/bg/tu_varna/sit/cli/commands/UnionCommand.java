package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFAOperations;

import java.util.List;

public class UnionCommand implements Command {
    private final AutomatonManager manager;

    public UnionCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        Integer otherId = CommandArguments.requireIntegerArgument(args, "union <automatonId>");
        if (otherId == null) {
            return;
        }

        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        NFA other = manager.getAutomatonById(otherId);
        if (other == null) {
            System.out.println("Няма автомат с ID " + otherId);
            return;
        }
        NFA result = NFAOperations.union(current, other);
        int newId = manager.addAutomaton(result);
        System.out.println("Обединението е създадено с ID: " + newId);
    }
}
