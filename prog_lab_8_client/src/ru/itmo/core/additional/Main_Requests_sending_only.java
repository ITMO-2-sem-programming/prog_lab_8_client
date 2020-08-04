package ru.itmo.core.additional;

import ru.itmo.core.common.User;
import ru.itmo.core.common.exchange.request.CommandRequest;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.response.CommandResponse;
import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.core.connection.ConnectionManager;
import ru.itmo.core.main.CommandManager;
import ru.itmo.core.main.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;


public class Main_Requests_sending_only {

    private static ConnectionManager connectionManager;
    private static User user;


    public static void main(String[] args) {

        Validator.setSymbolsForNullValues(new String[] {""});
        run();
    }


    public static void run()  {
        System.out.println("Client is running...");
        try {
            connectionManager = new ConnectionManager("localhost", 44231);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }


        String command;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


//        UserManager um = new UserManager();
//        boolean userRecognised = false;
//        while (!userRecognised) {
//            try {
//                user = um.recogniseUser(connectionManager, reader);
//                user = new User("maxim", "maxim");
//                userRecognised = true;
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//        }


        while (true) {
            try {
                System.out.println("Enter command : ");
                command = reader.readLine();
                System.out.println(runCommand(command));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Error: Unknown error during reading the input was occurred.");
            }
        }

    }


    public static String runCommand(String command) {
        return runCommand(connectionManager, user, command, false, null);
    }


    public static String runCommand(ConnectionManager connectionManager, User user, String command, boolean executionOfScript, Iterator<String> iterator) {

        CommandRequest commandRequest;
        CommandResponse commandResponse;
        Request request;
        Response response;


        try {
            commandRequest = CommandManager.executeCommand(command, executionOfScript, iterator);

//            if (commandRequest != null) commandRequest;

            if (commandRequest != null) {
                try {
                    commandRequest.setUser(user);
                    request = new Request(commandRequest);
                    connectionManager.sendRequest(request);

//                    while (true) {
//                        try {
//                            response = connectionManager.receiveResponse();
//                            break;
//                        } catch (StreamCorruptedException ignored) {}
//                    }

//                    System.out.println(response);

//                    return response.getCommandResponse().getMessage();
                    return "";
                } catch (IOException e) {
                    throw new IllegalArgumentException("Error: Can't connect server.");
                }
            }
            return "";
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }







    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public static User getUser() {
        return user;
    }
}



