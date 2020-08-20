package ru.itmo.core.command.userCommand.command.representation;



public class RemoveGreaterCommandRepresentation extends CommandRepresentation {


    public RemoveGreaterCommandRepresentation() {
        super(
                "remove_greater",
                "Command: remove_greater {element}" +
                        "\nDescription: Removes all the element greater than the specified one." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument: element (MusicBand)" +
                        "\n",
                false,
                true
        );
    }

}
