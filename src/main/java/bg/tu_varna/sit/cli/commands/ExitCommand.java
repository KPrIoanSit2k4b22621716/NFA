package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Излизане от програмата...");
        System.exit(0);
    }
}