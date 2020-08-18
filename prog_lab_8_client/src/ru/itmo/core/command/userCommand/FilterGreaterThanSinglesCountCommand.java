package ru.itmo.core.command.userCommand;


public class FilterGreaterThanSinglesCountCommand extends Command {

    private final static byte numberOfArguments = 1;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Incorrect 'singlesCount' value.");
        }
    }
}
