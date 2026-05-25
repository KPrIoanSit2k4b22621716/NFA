package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.cli.CommandArguments;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFARecognizer;

import java.util.List;

public class RecognizeCommand implements Command {
    private final AutomatonManager manager;

    public RecognizeCommand(AutomatonManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(List<String> args) {
        String word = CommandArguments.requireSingleArgument(args, "recognize <word>");
        if (word == null) {
            return;
        }

        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        boolean accepted = NFARecognizer.recognize(nfa, word);
        System.out.println("Думата \"" + word + "\" " + (accepted ? "СЕ приема" : "НЕ СЕ приема"));
    }
}
