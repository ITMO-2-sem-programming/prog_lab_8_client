package ru.itmo.core.command.userCommand.command.representation;



public class InsertCommandRepresentation extends CommandRepresentation {


    public InsertCommandRepresentation() {
        super(
                "insert",
                "Command: insert <key> {element}." +
                        "\nDescription: Adds new element with the following key (if the key doesn't exist)." +
                        "\nNumber of arguments: 2" +
                        "\n   First argument:  key     (Integer)" +
                        "\n   Second argument: element (MusicBand)" +
                        "\n",
                true,
                true
        );
    }
}
