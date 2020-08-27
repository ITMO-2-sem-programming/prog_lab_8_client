package ru.itmo.UI.controller;


import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ru.itmo.UI.Client;
import ru.itmo.UI.Main;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.AuthoriseUserServiceRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.AuthorizeUserServiceResponse;
import ru.itmo.core.encryption.Encryptor;
import ru.itmo.core.encryption.SHA_224;


public class LoginDialogController {

    private Main main;
    private Client client;

    private final Encryptor encryptor
            = new Encryptor(new SHA_224());

    @FXML
    private AnchorPane pane;

    private Stage stage;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;



    @FXML
    private void initialize() {
        stage = new Stage();
        stage.setTitle("Auth and reg");
        stage.setResizable(false);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

    }

    private void initializeAfterMainSet() {
        client = main.getClient();
    }


    @FXML
    private void handleAuthoriseButton() {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Incorrect data.");

        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);

        User user;

        if (loginField.getText().isEmpty()) {
            alert.setContentText("Login field must be filled in.");

        } else if (passwordField.getText().isEmpty()) {
            alert.setContentText("Password field must be filled in.");
        } else {
            user = new User(loginField.getText(), encryptor.encrypt(passwordField.getText()));

            client.addRequest(new AuthoriseUserServiceRequest(user));

            AuthorizeUserServiceResponse response = (AuthorizeUserServiceResponse) client.getServiceResponse();

            if ( ! response.isAuthorized() ) {
                alert.setContentText(response.getMessage());
            } else {
                client.setUser(user);
                stage.close();
                main.loadCollection();
                main.loadOwnedElementsID();
                main.loadCollectionOverview().getStage().show();
            }
        }

        if ( ! alert.getContentText().equals("")) {
            alert.showAndWait();
        }
    }

    public void setMain(Main main) {
        this.main = main;
        initializeAfterMainSet();
    }


    public Stage getStage() {
        return stage;
    }



}
