package ru.itmo.core.command.userCommand.command.representation;



public class FilterGreaterThanSinglesCountCommandRepresentation extends CommandRepresentation {


    public FilterGreaterThanSinglesCountCommandRepresentation() {
        super(
                "filter_greater_than_singles_count",
                "Command: filter_greater_than_singles_count <singlesCount>" +
                        "\nDescription: Prints the elements, which 'singlesCount' value is greater than the specified." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument:  singlesCount (int)" +
                        "\n",
                true,
                false
        );
    }
}
