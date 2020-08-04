package ru.itmo.core.commands;


public class ReplaceIfLowerCommand extends Command{

    private final static byte numberOfArguments = 1;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Incorrect 'key' value.");
        }
    }
}
