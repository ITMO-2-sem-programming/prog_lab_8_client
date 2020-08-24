package ru.itmo.core.command.representation;



public class ReplaceIfLowerCommandRepresentation extends CommandRepresentation {


    public ReplaceIfLowerCommandRepresentation() {
        super(
                "replace_if_lower",
                "Command: replace_if_lower <key> {element}" +
                        "\nDescription: Replaces the element of specified key with the specified element if the new one is less." +
                        "\nNumber of arguments 2: " +
                        "\n   First argument:  key     (Integer)" +
                        "\n   Second argument: element (MusicBand)" +
                        "\n",
                true,
                true
        );
    }
}
