package ru.itmo.core.command.userCommand.command;


import ru.itmo.core.command.userCommand.UserCommand;
import ru.itmo.core.common.classes.MusicBand;


public class RemoveLowerKeyCommand implements UserCommand {


    public RemoveLowerKeyCommand(Integer ID) {
        setID(ID);
    }


    private Integer ID;




    public Integer getID() {
        return ID;
    }


    private void setID(Integer ID) {

        if ( ! (ID >= 1))
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid id : '%s'"
                                    + "\n" + MusicBand.musicBandFieldsDescription.get("id"),
                            ID
                    )
            );

        this.ID = ID;

    }



}
