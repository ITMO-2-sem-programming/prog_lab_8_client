package ru.itmo.UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.itmo.UI.view.CollectionOverviewController;
import ru.itmo.UI.view.LoginDialogController;
import ru.itmo.UI.view.MusicBandEditController;
import ru.itmo.UI.view.RootPaneController;
import ru.itmo.core.common.classes.*;

import java.io.IOException;


public class Main extends Application {

    ObservableList<MusicBand> collection
            = FXCollections.observableArrayList();

    private Stage rootStage;
    private BorderPane rootPane;

    private AnchorPane loginDialogPane;
    private AnchorPane collectionOverviewPane;
    private AnchorPane musicBandEditPane;


    public Main() {

        collection.add(
                new MusicBand(
                        1,
                        "Beatles",
                        new Coordinates(1,2),
                        5,
                        2,
                        MusicGenre.HIP_HOP,
                        new Person(
                                "Van",
                                166L,
                                Color.WHITE,
                                Country.GERMANY,
                                new Location(2,
                                        4,
                                        "Berlin"
                                )
                        )
                ));

        collection.add(
                new MusicBand(
                        1,
                        "Beatles",
                        new Coordinates(1,2),
                        5,
                        2,
                        MusicGenre.HIP_HOP,
                        new Person(
                                "Van",
                                null,
                                null,
                                null,
                                new Location(2,
                                        4,
                                        "Berlin"
                                )
                        )
                ));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage rootStage) {

        this.rootStage = rootStage;

//        initRootPane();

//        initLoginDialogPane();
        initCollectionOverviewPane();
        rootStage.show();
    }


    /**
     * Creates a scene with LoginDialogPane and sets it to rootStage.
     * <br>Sets title of the stage.
     */
    private void initLoginDialogPane() {

        try {
            loadLoginDialogPane();

            Scene scene = new Scene(loginDialogPane);

            rootStage.setScene(scene);

            rootStage.setResizable(false);

            rootStage.setTitle("Authorisation/Registration");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initCollectionOverviewPane() {

        try {
            loadCollectionOverviewPane();

            Scene scene = new Scene(collectionOverviewPane);

            rootStage.setScene(scene);

            rootStage.setResizable(false);

            rootStage.setTitle("CollectionOverview");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void initRootPane() {

        try {
            loadRootPane();

            Scene scene = new Scene(rootPane);

            rootStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Deprecated
    private Pane loadPane(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(path));
        return loader.load();
    }


    private void loadRootPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/RootPane.fxml"));
        rootPane = loader.load();

        ((RootPaneController) loader.getController()).setMain(this);

    }


    private void loadMusicBandEditPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MusicBandEditPane.fxml"));
        musicBandEditPane = loader.load();

        ((MusicBandEditController) loader.getController()).setMain(this);

    }


    private void loadLoginDialogPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/LoginDialogPane.fxml"));
        loginDialogPane = loader.load();

        ((LoginDialogController) loader.getController()).setMain(this);

    }


    private void loadCollectionOverviewPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/CollectionOverviewPane.fxml"));
        collectionOverviewPane = loader.load();

        ((CollectionOverviewController) loader.getController()).setMain(this);

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


    public ObservableList<MusicBand> getCollection() {
        return collection;
    }
}
