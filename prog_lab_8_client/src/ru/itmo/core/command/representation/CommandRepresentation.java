package ru.itmo.core.command.representation;


public abstract class CommandRepresentation {


    private final String commandName;
    private final String commandDescription;
    private final boolean hasSimpleArgument;
    private final boolean hasElementArgument;



    protected CommandRepresentation(String commandName, String commandDescription, boolean hasSimpleArgument, boolean hasElementArgument) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
        this.hasSimpleArgument = hasSimpleArgument;
        this.hasElementArgument = hasElementArgument;
    }


    public String getCommandName() {
        return commandName;
    }


    public String getCommandDescription() {
        return commandDescription;
    }


    public boolean hasSimpleArgument() {
        return hasSimpleArgument;
    }


    public boolean hasElementArgument() {
        return hasElementArgument;
    }



    @Override
    public String toString() {
        return commandName;
    }
}
