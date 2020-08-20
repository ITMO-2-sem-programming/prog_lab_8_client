package ru.itmo.core.command.userCommand.command;


import ru.itmo.core.command.userCommand.UserCommand;
import ru.itmo.core.common.classes.MusicBand;



public class RemoveGreaterCommand implements UserCommand {


    private MusicBand element;



    public RemoveGreaterCommand(MusicBand element) {
        setElement(element);
    }




    public MusicBand getElement() {
        return element;
    }


    private void setElement(MusicBand element) {

        if (element == null)
            throw new IllegalArgumentException("Element can't ve null.");

        this.element = element;
    }



}