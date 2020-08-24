package ru.itmo.core.command.representation;



public class ClearCommandRepresentation extends CommandRepresentation {


    public ClearCommandRepresentation() {
        super(
                "clear",
                "\nCommand: clear" +
                        "\nDescription: Deletes all the owned elements from the collection." +
                        "\nNumber of arguments: 0" +
                        "\n",
                false,
                false
        );
    }
}
