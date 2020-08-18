package ru.itmo.core.command.userCommand;

import ru.itmo.core.personalExceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClearCommand extends Command {

    private final static byte numberOfArguments = 0;

    public static boolean validateArguments(String[] args, boolean safeMode) {

        checkNumberOfArguments(args, numberOfArguments);

        if (safeMode) {
            System.out.println("Warning: Command will delete all the MusicBands that were created by you.\n" +
                    "Enter 'Yes' or 'Y' to confirm, other input will reject this command:");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String confirmation = reader.readLine().toLowerCase();

                if (confirmation.equals("yes") || confirmation.equals("y")) {
                    return true;

                } else {
                    System.out.println("Command was rejected. Program continues working.");
                    return false;
                }
            } catch (Exception e) {
                throw new InvalidCommandException("Error: Unresolved error was occurred. Command was rejected.");
            }

        } else
            return true;
    }
}
