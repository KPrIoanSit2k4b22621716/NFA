package bg.tu_varna.sit.cli;

import java.util.List;

public final class CommandArguments {
    private CommandArguments() {}

    public static boolean requireNoArgs(List<String> args, String usage) {
        if (!args.isEmpty()) {
            printUsage(usage);
            return false;
        }
        return true;
    }

    public static String requireSingleArgument(List<String> args, String usage) {
        if (args.size() != 1) {
            printUsage(usage);
            return null;
        }
        return args.get(0);
    }

    public static Integer requireIntegerArgument(List<String> args, String usage) {
        String value = requireSingleArgument(args, usage);
        if (value == null) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println("Аргументът трябва да бъде цяло число.");
            printUsage(usage);
            return null;
        }
    }

    private static void printUsage(String usage) {
        System.out.println("Невалидни аргументи. Използване: " + usage);
    }
}
