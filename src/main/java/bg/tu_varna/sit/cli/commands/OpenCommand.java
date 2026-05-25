package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;

import java.util.List;

public class OpenCommand implements Command {
    private final AutomatonManager manager;

    public OpenCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        String filePath = CommandArguments.requireSingleArgument(args, "open <filePath>");
        if (filePath == null) {
            return;
        }
        manager.openFile(filePath);
    }
}
