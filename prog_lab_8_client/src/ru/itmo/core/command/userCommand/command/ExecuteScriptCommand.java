package ru.itmo.core.command.userCommand.command;


import ru.itmo.core.command.userCommand.UserCommand;

import java.io.File;



public class ExecuteScriptCommand implements UserCommand {


    String filePath;



    public ExecuteScriptCommand(String filePath) {
        setFilePath(filePath);
    }




    public String getFilePath() {
        return filePath;
    }


    private void setFilePath(String filePath) {

        File file = new File(filePath);

        if (
                ! file.exists()
                || file.isDirectory()
                || ! file.canRead()
        ) throw new IllegalArgumentException("Invalid file path (name). Possible reasons : file : doesn't exist; is a directory; can't be read.");

        this.filePath = filePath;

    }



}
