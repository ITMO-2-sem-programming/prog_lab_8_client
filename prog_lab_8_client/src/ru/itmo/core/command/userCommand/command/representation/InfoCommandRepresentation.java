package ru.itmo.core.command.userCommand.command.representation;



public class InfoCommandRepresentation extends CommandRepresentation {


    public InfoCommandRepresentation() {
        super(
                "info",
                "Command: info" +
                        "\nDescription: Prints the information about collection." +
                        "\nNumber of arguments: 0" +
                        "\n",
                false,
                false);
    }
}
