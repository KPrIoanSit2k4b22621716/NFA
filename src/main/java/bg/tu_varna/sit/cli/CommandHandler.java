package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.model.*;
import bg.tu_varna.sit.cli.CommandParser.Command;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final AutomatonManager manager;
    private final Map<String, CommandExecutor> executors;

    private interface CommandExecutor {
        void execute(Command cmd);
    }

    public CommandHandler(AutomatonManager manager) {
        this.manager = manager;
        this.executors = new HashMap<>();

        executors.put("help", cmd -> HelpPrinter.print());
        executors.put("exit", cmd -> { System.out.println("Излизане от програмата..."); System.exit(0); });
        executors.put("close", cmd -> manager.closeFile());
        executors.put("list", cmd -> manager.listAutomata());
        executors.put("print", cmd -> manager.printAutomaton());
        executors.put("save", cmd -> manager.saveFile());
        executors.put("deterministic", cmd -> handleDeterministic());
        executors.put("empty", cmd -> handleEmpty());
        executors.put("star", cmd -> handleStar());
        executors.put("determinize", cmd -> handleDeterminize());


        executors.put("open", cmd -> {
            if (cmd.getArgCount() < 1) {
                System.out.println("Грешка: open <file>");
                return;
            }
            manager.openFile(cmd.getArg(0));
        });
        executors.put("saveas", cmd -> {
            if (cmd.getArgCount() < 1) {
                System.out.println("Грешка: saveas <file>");
                return;
            }
            manager.saveAsFile(cmd.getArg(0));
        });
        executors.put("recognize", cmd -> handleRecognize(cmd));
        executors.put("union", cmd -> handleUnion(cmd));
        executors.put("concat", cmd -> handleConcat(cmd));
        executors.put("reg", cmd -> handleRegex(cmd));
    }

    public void execute(Command cmd) {
        CommandExecutor executor = executors.get(cmd.getName());
        if (executor == null) {
            System.out.println("Неизвестна команда: " + cmd.getName());
            return;
        }
        executor.execute(cmd);
    }


    private void handleRecognize(Command cmd) {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        if (cmd.getArgCount() < 1) {
            System.out.println("Грешка: recognize <word>");
            return;
        }
        String word = cmd.getArg(0);
        boolean accepted = NFARecognizer.recognize(nfa, word);
        System.out.println("Думата \"" + word + "\" " + (accepted ? "СЕ приема" : "НЕ СЕ приема"));
    }

    private void handleDeterministic() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        System.out.println("Автоматът е " + (NFADeterminism.isDeterministic(nfa) ? "ДЕТЕРМИНИРАН" : "НЕДЕТЕРМИНИРАН"));
    }

    private void handleEmpty() {
        NFA nfa = manager.getCurrentAutomaton();
        if (nfa == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        System.out.println("Езикът на автомата е " + (NFAOperations.isEmptyLanguage(nfa) ? "ПРАЗЕН" : "НЕ Е ПРАЗЕН"));
    }

    private void handleUnion(Command cmd) {
        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        if (cmd.getArgCount() < 1) {
            System.out.println("Грешка: union <id>");
            return;
        }
        try {
            int otherId = Integer.parseInt(cmd.getArg(0));
            NFA other = manager.getAutomatonById(otherId);
            if (other == null) {
                System.out.println("Няма автомат с ID " + otherId);
                return;
            }
            NFA result = NFAOperations.union(current, other);
            int newId = manager.addAutomaton(result);
            System.out.println("Обединението е създадено с ID: " + newId);
        } catch (NumberFormatException e) {
            System.out.println("Грешка: ID трябва да е число");
        }
    }

    private void handleConcat(Command cmd) {
        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        if (cmd.getArgCount() < 1) {
            System.out.println("Грешка: concat <id>");
            return;
        }
        try {
            int otherId = Integer.parseInt(cmd.getArg(0));
            NFA other = manager.getAutomatonById(otherId);
            if (other == null) {
                System.out.println("Няма автомат с ID " + otherId);
                return;
            }
            NFA result = NFAOperations.concat(current, other);
            int newId = manager.addAutomaton(result);
            System.out.println("Конкатенацията е създадена с ID: " + newId);
        } catch (NumberFormatException e) {
            System.out.println("Грешка: ID трябва да е число");
        }
    }

    private void handleStar() {
        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        NFA result = NFAOperations.star(current);
        int newId = manager.addAutomaton(result);
        System.out.println("Звездата на автомата е създадена с ID: " + newId);
    }

    private void handleDeterminize() {
        NFA current = manager.getCurrentAutomaton();
        if (current == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        NFA dfa = NFADeterminism.determinize(current);
        int newId = manager.addAutomaton(dfa);
        System.out.println("Детерминираният автомат е създаден с ID: " + newId);
    }

    private void handleRegex(Command cmd) {
        if (cmd.getArgCount() < 1) {
            System.out.println("Грешка: reg <regex>");
            return;
        }
        try {
            NFA nfa = RegexToNFA.fromRegex(cmd.getArg(0));
            int newId = manager.addAutomaton(nfa);
            System.out.println("Автоматът от регулярен израз е създаден с ID: " + newId);
        } catch (Exception e) {
            System.out.println("Грешка при парсване на regex: " + e.getMessage());
        }
    }
}