package ru.itmo.core.command.userCommand.command.representation;



public class ShowCommandRepresentation extends CommandRepresentation {


    public ShowCommandRepresentation() {
        super(
                "show",
                "Command: show " +
                        "\nDescription: Prints all the elements in collection." +
                        "\nNumber of arguments: 0" +
                        "\n",
                false,
                false
        );
    }
}
