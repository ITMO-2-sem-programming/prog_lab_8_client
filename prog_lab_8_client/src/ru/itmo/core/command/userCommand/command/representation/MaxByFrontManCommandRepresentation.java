package ru.itmo.core.command.userCommand.command.representation;



public class MaxByFrontManCommandRepresentation extends CommandRepresentation {


    public MaxByFrontManCommandRepresentation() {
        super(
                "max_by_front_man",
                "Command: max_by_front_man" +
                        "\n     Description: Prints element, which field 'frontMan' is the greatest." +
                        "\n     Number of arguments: 0" +
                        "\n",
                false,
                false
        );
    }
}
