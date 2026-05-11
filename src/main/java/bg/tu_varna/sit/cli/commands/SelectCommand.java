package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;

public class SelectCommand implements Command {
    private final AutomatonManager manager;
    private final int id;

    public SelectCommand(AutomatonManager manager, int id) {
        this.manager = manager;
        this.id = id;
    }

    @Override
    public void execute() {
        manager.selectAutomaton(id);
    }
}