package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.model.*;
import bg.tu_varna.sit.cli.CommandParser.Command;

public class CommandHandler {
    private final AutomatonManager manager;

    public CommandHandler(AutomatonManager manager) {
        this.manager = manager;
    }

    public void execute(Command cmd) {
        switch (cmd.getName()) {
            case "help":
                HelpPrinter.print();
                break;
            case "exit":
                System.out.println("Излизане от програмата...");
                System.exit(0);
                break;
            case "open":
                if (cmd.getArgCount() < 1) {
                    System.out.println("Грешка: open <file>");
                    return;
                }
                manager.openFile(cmd.getArg(0));
                break;
            case "close":
                manager.closeFile();
                break;
            case "list":
                manager.listAutomata();
                break;
            case "print":
                manager.printAutomaton();
                break;
            case "save":
                manager.saveFile();
                break;
            case "saveas":
                if (cmd.getArgCount() < 1) {
                    System.out.println("Грешка: saveas <file>");
                    return;
                }
                manager.saveAsFile(cmd.getArg(0));
                break;
            case "recognize":
                handleRecognize(cmd);
                break;
            case "deterministic":
                handleDeterministic();
                break;
            case "empty":
                handleEmpty();
                break;
            case "union":
                handleUnion(cmd);
                break;
            case "concat":
                handleConcat(cmd);
                break;
            case "star":
                handleStar();
                break;
            case "determinize":
                handleDeterminize();
                break;
            case "reg":
                handleRegex(cmd);
                break;
            default:
                System.out.println("Неизвестна команда: " + cmd.getName());
        }
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