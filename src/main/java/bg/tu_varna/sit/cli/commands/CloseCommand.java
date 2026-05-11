package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;

public class CloseCommand implements Command {
    private final AutomatonManager manager;

    public CloseCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.closeFile();
    }
}