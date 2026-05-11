package bg.tu_varna.sit.cli;

public class HelpPrinter {

    public static void print() {
        System.out.println("\nNFA COMMAND LINE INTERFACE");
        System.out.println("===========================");
        System.out.println("Usage: <COMMAND> [arguments]");
        System.out.println("\nAvailable commands:\n");


        int maxLength = 0;
        for (CommandType cmd : CommandType.values()) {
            maxLength = Math.max(maxLength, cmd.getCommand().length());
        }


        int padding = maxLength + 4;

        for (CommandType cmd : CommandType.values()) {
            String command = cmd.getCommand().toUpperCase();
            String description = cmd.getDescription();


            System.out.printf("  %-" + padding + "s %s%n", command, description);
        }

        System.out.println("\nExamples:");
        System.out.println("  > OPEN test.json");
        System.out.println("  > RECOGNIZE ab");
        System.out.println("  > UNION 1");
        System.out.println("  > REG a|b*");
        System.out.println("\nFor more details, visit the project documentation.\n");
    }
}