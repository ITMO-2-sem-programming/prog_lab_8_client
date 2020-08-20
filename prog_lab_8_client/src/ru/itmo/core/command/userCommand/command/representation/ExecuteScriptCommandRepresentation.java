package ru.itmo.core.command.userCommand.command.representation;



public class ExecuteScriptCommandRepresentation extends CommandRepresentation {


    public ExecuteScriptCommandRepresentation() {
        super(
                "execute_script",
                "Command: execute_script <file_path>" +
                        "\nDescription: Executes the commands from the file." +
                        "\nNumber of arguments: 1" +
                        "\n   Argument:  file_path (String)" +
                        "\n",
                true,
                false
        );
    }
}
