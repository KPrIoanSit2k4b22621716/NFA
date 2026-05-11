package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.HelpPrinter;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        HelpPrinter.print();
    }
}