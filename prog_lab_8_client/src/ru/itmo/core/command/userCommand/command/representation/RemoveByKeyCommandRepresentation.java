package ru.itmo.core.command.userCommand.command.representation;



public class RemoveByKeyCommandRepresentation extends CommandRepresentation {


    public RemoveByKeyCommandRepresentation() {
        super(
                "remove_key",
                "Command: remove_key <key>" +
                        "\nDescription: Removes an element with the specified key." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument: key (Integer)" +
                        "\n",
                true,
                false
        );
    }
}
