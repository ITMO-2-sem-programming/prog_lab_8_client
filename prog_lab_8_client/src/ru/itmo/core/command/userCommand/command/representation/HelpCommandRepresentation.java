package ru.itmo.core.command.userCommand.command.representation;



public class HelpCommandRepresentation extends CommandRepresentation {


    public HelpCommandRepresentation() {
        super(
                "help",
                "Command: help" +
                        "\nDescription: Prints the information about all of the commands." +
                        "\nNumber of arguments: 0" +
                        "\n",
                false,
                false);
    }
}
