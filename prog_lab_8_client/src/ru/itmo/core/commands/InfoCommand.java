package ru.itmo.core.commands;


public class InfoCommand extends Command {

    private final static byte numberOfArguments = 0;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

    }
}
