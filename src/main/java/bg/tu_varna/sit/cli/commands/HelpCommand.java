package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.cli.HelpPrinter;

import java.util.List;

public class HelpCommand implements Command {
    @Override
    public void execute(List<String> args) {
        if (!CommandArguments.requireNoArgs(args, "help")) {
            return;
        }
        HelpPrinter.print();
    }
}
