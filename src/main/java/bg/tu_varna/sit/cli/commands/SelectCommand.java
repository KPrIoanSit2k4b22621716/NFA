package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;

import java.util.List;

public class SelectCommand implements Command {
    private final AutomatonManager manager;

    public SelectCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        Integer id = CommandArguments.requireIntegerArgument(args, "select <automatonId>");
        if (id == null) {
            return;
        }
        manager.selectAutomaton(id);
    }
}
