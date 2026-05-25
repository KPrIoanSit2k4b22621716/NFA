package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;

import java.util.List;

public class ListCommand implements Command {
    private final AutomatonManager manager;

    public ListCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        if (!CommandArguments.requireNoArgs(args, "list")) {
            return;
        }
        manager.listAutomata();
    }
}
