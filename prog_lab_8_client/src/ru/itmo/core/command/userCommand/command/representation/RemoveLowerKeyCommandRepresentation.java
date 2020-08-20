package ru.itmo.core.command.userCommand.command.representation;



public class RemoveLowerKeyCommandRepresentation extends CommandRepresentation {


    public RemoveLowerKeyCommandRepresentation() {
        super(
                "remove_lower_key",
                "Command: remove_lower_key <key>" +
                        "\nDescription: Removes all the elements, which key is less than the specified one." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument: key (Integer)" +
                        "\n",
                true,
                false
        );
    }
}
