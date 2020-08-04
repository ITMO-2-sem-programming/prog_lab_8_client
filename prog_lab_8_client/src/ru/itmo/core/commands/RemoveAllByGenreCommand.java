package ru.itmo.core.commands;


import ru.itmo.core.common.classes.MusicGenre;

public class RemoveAllByGenreCommand extends Command{

    private final static byte numberOfArguments = 1;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

        try {
            MusicGenre.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: Incorrect 'musicGenre' value. Please, use one of the following:\n" + MusicGenre.showValues());
        }
    }
}
