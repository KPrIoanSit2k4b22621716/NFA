package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;

public class SaveAsCommand implements Command {
    private final AutomatonManager manager;
    private final String filePath;

    public SaveAsCommand(AutomatonManager manager, String filePath) {
        this.manager = manager;
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        manager.saveAsFile(filePath);
    }
}