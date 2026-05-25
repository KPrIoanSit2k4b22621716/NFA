package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.CommandArguments;

import java.util.List;

public class ExitCommand implements Command {
    @Override
    public void execute(List<String> args) {
        if (!CommandArguments.requireNoArgs(args, "exit")) {
            return;
        }
        System.out.println("Излизане от програмата...");
        System.exit(0);
    }
}
