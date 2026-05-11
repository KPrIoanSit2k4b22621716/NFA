package bg.tu_varna.sit.cli;

import java.util.*;

public class CommandParser {

    public static Command parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new Command("", new ArrayList<>());
        }

        String[] parts = input.trim().split("\\s+");
        String commandName = parts[0].toLowerCase();
        List<String> args = new ArrayList<>();


        for (int i = 1; i < parts.length; i++) {
            args.add(parts[i]);
        }

        return new Command(commandName, args);
    }

    public static class Command {
        private final String name;
        private final List<String> args;

        public Command(String name, List<String> args) {
            this.name = name;
            this.args = args;
        }

        public String getName() {
            return name;
        }

        public List<String> getArgs() {
            return args;
        }

        public int getArgCount() {
            return args.size();
        }

        public String getArg(int index) {
            if (index >= 0 && index < args.size()) {
                return args.get(index);
            }
            return null;
        }

        @Override
        public String toString() {
            return "Command{" + name + " " + args + "}";
        }
    }
}