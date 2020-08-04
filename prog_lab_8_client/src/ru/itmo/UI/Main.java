package ru.itmo.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {


    private Stage rootStage;
    private BorderPane rootPane;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage rootStage) {

        setRootStage(rootStage);

//        initRootPane();

        initLoginDialogPane();
        rootStage.show();
    }

    /**
     * Creates a scene with LoginDialogPane and sets it to rootStage.
     * <br>Sets title of the stage.
     */
    private void initLoginDialogPane() {

        try {
            AnchorPane loginDialogPane = loadLoginDialogPane();
            Scene scene = new Scene(loginDialogPane);

            rootStage.setScene(scene);

            rootStage.setResizable(false);

            rootStage.setTitle("Authorisation/Registration");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private void initRootPane() {

        try {
            rootPane = loadRootPane();

            Scene scene = new Scene(rootPane);
            rootStage.setScene(scene);

//            rootStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


//    private void showLoginDialog() {
//
//        try {
//            AnchorPane loginDialogLayout = loadLoginDialogPane();
//            rootPane.setCenter(loginDialogLayout);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private Pane loadPane(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(path));
        return loader.load();
    }


    private BorderPane loadRootPane() throws IOException {
        return (BorderPane) loadPane("view/RootPane.fxml");
    }


    private AnchorPane loadMusicBandOverviewPane() throws IOException {
        return (AnchorPane) loadPane("view/MusicBandOverviewPane.fxml");
    }


    private AnchorPane loadLoginDialogPane() throws IOException {
        return (AnchorPane) loadPane("view/LoginDialogPane.fxml");
    }




    public Stage getRootStage() {
        return rootStage;
    }


    private void setRootStage(Stage rootStage) {
        this.rootStage = rootStage;
    }


    public BorderPane getRootPane() {
        return rootPane;
    }


    private void setRootPane(BorderPane rootPane) {
        this.rootPane = rootPane;
    }

}
