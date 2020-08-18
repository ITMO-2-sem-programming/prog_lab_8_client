package ru.itmo.core.command.userCommand;

import ru.itmo.core.command.Command;
import ru.itmo.core.common.classes.MusicBand;


public abstract class UserCommand extends Command {


    protected boolean hasSimpleArgument;
    protected boolean hasElementArgument;

    protected Object simpleArgument;
    protected MusicBand elementArgument;


    public UserCommand(boolean hasSimpleArgument, boolean hasElementArgument) {
        this.hasSimpleArgument = hasSimpleArgument;
        this.hasElementArgument = hasElementArgument;
    }




    public boolean hasSimpleArgument() {
        return hasSimpleArgument;
    }

    public boolean hasElementArgument() {
        return hasElementArgument;
    }


    public Object getSimpleArgument() {
        return simpleArgument;
    }

    public void setSimpleArgument(Object simpleArgument) {
        if ( ! hasSimpleArgument )
            throw new IllegalArgumentException("No arguments were expected.");
        this.simpleArgument = simpleArgument;
    }

    public MusicBand getElementArgument() {
        return elementArgument;
    }

    public void setElementArgument(MusicBand elementArgument) {
        if ( ! hasElementArgument )
            throw new IllegalArgumentException("No arguments were expected.");
        this.elementArgument = elementArgument;
    }
}
