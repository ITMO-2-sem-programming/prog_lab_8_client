
package ru.itmo.core.main;

import ru.itmo.core.common.User;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.request.ServiceRequest;
import ru.itmo.core.common.exchange.request.ServiceRequestStatus;
import ru.itmo.core.common.exchange.response.IllegalResponseException;
import ru.itmo.core.common.exchange.response.Response;
import ru.itmo.core.common.exchange.response.ServiceResponse;
import ru.itmo.core.common.exchange.response.ServiceResponseStatus;
import ru.itmo.core.connection.ConnectionManager;
import ru.itmo.core.encryption.Encryptor;
import ru.itmo.core.encryption.SHA_224;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Date;


public class UserManager {


    private String exitCommand = "exit";
    private String registrationKey = "r";
    private String authorisationKey = "a";
    private Encryptor encryptor;

    private static final int WAITING_FOR_RESPONSE_TIME = 2 * 1000;



    public User recogniseUser(ConnectionManager connectionManager, BufferedReader reader) throws IOException {
        String inp = "";
        User user = null;
        ServiceResponse serviceResponse = new ServiceResponse();
        encryptor = new Encryptor(new SHA_224());

        boolean userIsRecognised = false;


        while (!userIsRecognised) {
            System.out.printf("Enter '%s' for registration or '%s' for authorisation : \n", registrationKey, authorisationKey);
            inp = reader.readLine().trim().toLowerCase();
            checkForExitCommand(inp);

            if (inp.equals(registrationKey)) {
                user = getUserDataForRegistration(reader);
                serviceResponse = sendServiceRequest(connectionManager, user, ServiceRequestStatus.REGISTRATION);
            } else if (inp.equals(authorisationKey)) {
                user = getUserDataForAuthorisation(reader);
                serviceResponse = sendServiceRequest(connectionManager, user, ServiceRequestStatus.AUTHORISATION);

            } else {
                System.out.println("Incorrect key.");
                continue;
            }


            if (serviceResponse.getStatus() == ServiceResponseStatus.AUTHORISED) {
                userIsRecognised  = true;
                System.out.println("You were authorised successfully.");
            }
            else if (serviceResponse.getStatus() == ServiceResponseStatus.REGISTERED) {
                userIsRecognised = true;
                System.out.println("You were registered successfully.");
            } else if (serviceResponse.getStatus() == ServiceResponseStatus.ERROR){
                System.out.println(serviceResponse.getMessage());
            } else throw new IllegalResponseException("'ServiceResponseStatus' was changed, however the code did not.");
        }

        return user;
    }


    private ServiceResponse sendServiceRequest(ConnectionManager connectionManager, User user, ServiceRequestStatus status) throws IOException {

        try {
            connectionManager.sendRequest(new Request(new ServiceRequest(user, status)));

            Response response;
            Date startWaiting = new Date();
            while (true) {
                try {
                    response = connectionManager.receiveResponse();
                    break;
                } catch (StreamCorruptedException ignored) {
                    if (new Date().getTime() - startWaiting.getTime() > WAITING_FOR_RESPONSE_TIME) {
                        throw new IllegalArgumentException("Error: Can't get response from server.");
                    }
                }
            }

//            System.out.println(response);
            return response.getServiceResponse();

        } catch (ClassNotFoundException e) {
            throw new IOException(e.getMessage());

        } catch (IOException e) {
            throw new IOException("Error: Can't connect sever.");
        }
    }



    private User getUserDataForRegistration(BufferedReader reader) throws IOException {
        return getUserData(reader, new String[] {
                "Create user name : ",
                "Create password : "
        });
    }


    private User getUserDataForAuthorisation(BufferedReader reader) throws IOException{
        return getUserData(reader, new String[] {
                "Enter user name : ",
                "Enter password : "
        });
    }


    private User getUserData(BufferedReader reader, String[] requestMessages) throws IOException {
        String inp = "";
        User user = new User();
        boolean correctLogin = false;
        boolean correctPassword = false;


        while (!correctLogin) {
            System.out.println(requestMessages[0]);
            inp = reader.readLine().trim();
            checkForExitCommand(inp);

            try {
                validateNewLogin(inp);
                user.setLogin(inp);
                correctLogin = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        while (!correctPassword) {
            System.out.println(requestMessages[1]);
            inp = reader.readLine().trim();
            checkForExitCommand(inp);

            try {
                validateNewPassword(inp);
                user.setPassword(encryptor.encrypt(inp));
                correctPassword = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        return user;
    }



    private void validateNewLogin(String login) {
        login = login.trim();
        if (login.length() < 4) throw new IllegalArgumentException("Error: Login length can't be less than 4 symbols");
    }

    private void validateNewPassword(String password) {
        password = password.trim();
        if (password.length() < 4) throw new IllegalArgumentException("Error: Password length can't be less than 4 symbols");
    }

    public void checkForExitCommand(String inp) {
        if (inp.equals(exitCommand)) System.exit(0);
    }


    public String getExitCommand() {
        return exitCommand;
    }

    public void setExitCommand(String exitCommand) {
        this.exitCommand = exitCommand;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public String getAuthorisationKey() {
        return authorisationKey;
    }

    public void setAuthorisationKey(String authorisationKey) {
        this.authorisationKey = authorisationKey;
    }
}


