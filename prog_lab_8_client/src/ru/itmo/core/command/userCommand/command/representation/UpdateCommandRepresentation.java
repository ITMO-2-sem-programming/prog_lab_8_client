package ru.itmo.core.command.userCommand.command.representation;



public class UpdateCommandRepresentation extends CommandRepresentation {


    public UpdateCommandRepresentation() {
        super(
                "update",
                "Command: update <id> {element}" +
                        "\nDescription: Updates value of the element with following 'ID'." +
                        "\nNumber of arguments: 2" +
                        "\n   First argument:  id      (Integer)" +
                        "\n   Second argument: element (MusicBand)" +
                        "\n",
                true,
                true);
    }
}




