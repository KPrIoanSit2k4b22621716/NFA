package bg.tu_varna.sit.cli.commands;

import bg.tu_varna.sit.cli.Command;
import bg.tu_varna.sit.cli.AutomatonManager;
import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.model.NFARecognizer;

public class RecognizeCommand implements Command {
    private final AutomatonManager manager;
    private final String word;

    public RecognizeCommand(AutomatonManager manager, String word) {
        this.manager = manager;
        this.word = word;
    }

    @Override
    public void execute() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        boolean accepted = NFARecognizer.recognize(nfa, word);
        System.out.println("Думата \"" + word + "\" " + (accepted ? "СЕ приема" : "НЕ СЕ приема"));
    }
}