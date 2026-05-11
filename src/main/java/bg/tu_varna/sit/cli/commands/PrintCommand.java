package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;

public class PrintCommand implements Command {
    private final AutomatonManager manager;

    public PrintCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.printAutomaton();
    }
}