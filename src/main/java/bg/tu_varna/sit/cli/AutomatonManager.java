package bg.tu_varna.sit.cli;

import bg.tu_varna.sit.model.NFA;
import bg.tu_varna.sit.io.AutomatonFileManager;
import java.util.*;

public class AutomatonManager {
    private Map<Integer, NFA> automataRegistry = new HashMap<>();
    private int nextId = 1;
    private NFA currentAutomaton = null;
    private String currentFilePath = null;

    public NFA getCurrentAutomaton() { return currentAutomaton; }
    public String getCurrentFilePath() { return currentFilePath; }

    public int openFile(String filePath) {
        try {
            NFA nfa = AutomatonFileManager.loadFromFile(filePath);
            int id = nextId++;
            automataRegistry.put(id, nfa);
            currentAutomaton = nfa;
            currentFilePath = filePath;
            System.out.println("Successfully opened " + filePath + " (ID: " + id + ")");
            return id;
        } catch (Exception e) {
            System.out.println("Файлът не съществува. Създава се нов празен автомат.");
            NFA newNFA = new NFA();
            int id = nextId++;
            automataRegistry.put(id, newNFA);
            currentAutomaton = newNFA;
            currentFilePath = filePath;
            System.out.println("Successfully created new automaton " + filePath + " (ID: " + id + ")");
            return id;
        }
    }

    public void closeFile() {
        if (currentAutomaton == null) {
            System.out.println("Няма отворен файл за затваряне.");
            return;
        }
        System.out.println("Successfully closed " + currentFilePath);
        currentAutomaton = null;
        currentFilePath = null;
    }

    public void saveFile() {
        if (currentAutomaton == null) {
            System.out.println("Няма отворен автомат за записване.");
            return;
        }
        if (currentFilePath == null) {
            System.out.println("Няма файл. Използвайте 'saveas <file>'.");
            return;
        }
        try {
            AutomatonFileManager.saveToFile(currentAutomaton, currentFilePath);
            System.out.println("Successfully saved " + currentFilePath);
        } catch (Exception e) {
            System.out.println("Грешка при запис: " + e.getMessage());
        }
    }

    public void saveAsFile(String newFilePath) {
        if (currentAutomaton == null) {
            System.out.println("Няма отворен автомат за записване.");
            return;
        }
        try {
            AutomatonFileManager.saveToFile(currentAutomaton, newFilePath);
            currentFilePath = newFilePath;
            System.out.println("Successfully saved " + newFilePath);
        } catch (Exception e) {
            System.out.println("Грешка при запис: " + e.getMessage());
        }
    }

    public void listAutomata() {
        if (automataRegistry.isEmpty()) {
            System.out.println("Няма заредени автомати.");
            return;
        }
        System.out.println("\nЗаредени автомати:");
        for (Map.Entry<Integer, NFA> entry : automataRegistry.entrySet()) {
            int id = entry.getKey();
            NFA nfa = entry.getValue();
            boolean isCurrent = (currentAutomaton == nfa);
            String marker = isCurrent ? " <- текущ" : "";
            System.out.println("  ID " + id + ": " + nfa + marker);
        }
        System.out.println();
    }

    public void printAutomaton() {
        if (currentAutomaton == null) {
            System.out.println("Няма отворен автомат.");
            return;
        }
        System.out.println("\nАвтомат (ID: " + getIdOfCurrentAutomaton() + "):");
        System.out.println("  Стартово състояние: " + currentAutomaton.getStartState());
        System.out.println("  Финални състояния: " + currentAutomaton.getFinalStates());
        System.out.println("  Азбука: " + currentAutomaton.getAlphabet());
        System.out.println("\n  Преходи:");
        if (currentAutomaton.getTransitions().isEmpty()) {
            System.out.println("    (няма преходи)");
        } else {
            for (var t : currentAutomaton.getTransitions()) {
                System.out.println("    " + t);
            }
        }
        System.out.println();
    }

    public NFA getAutomatonById(int id) {
        return automataRegistry.get(id);
    }

    public int addAutomaton(NFA nfa) {
        int id = nextId++;
        automataRegistry.put(id, nfa);
        return id;
    }

    private int getIdOfCurrentAutomaton() {
        for (Map.Entry<Integer, NFA> entry : automataRegistry.entrySet()) {
            if (entry.getValue() == currentAutomaton) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public void selectAutomaton(int id) {
        NFA selected = automataRegistry.get(id);
        if (selected == null) {
            System.out.println("No automaton with ID " + id);
            return;
        }

        currentAutomaton = selected;
        currentFilePath = null;

        System.out.println("Switched to automaton ID: " + id);
    }
}