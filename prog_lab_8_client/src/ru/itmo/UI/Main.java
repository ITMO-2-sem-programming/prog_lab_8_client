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
import ru.itmo.UI.controller.CollectionOverviewController;
import ru.itmo.UI.controller.LoginDialogController;
import ru.itmo.UI.controller.EditMusicBandDialogController;
import ru.itmo.UI.controller.RootPaneController;
import ru.itmo.core.common.classes.*;

import java.io.IOException;


public class Main extends Application {

    ObservableList<MusicBand> collection
            = FXCollections.observableArrayList();


    private Stage rootStage;
    private BorderPane rootPane;

    private AnchorPane loginDialogPane;
    private AnchorPane collectionOverviewPane;
    private BorderPane editMusicBandDialogPane;

    private RootPaneController rootPaneController;
    private CollectionOverviewController collectionOverviewController;
    private EditMusicBandDialogController editMusicBandDialogController;
    private LoginDialogController loginDialogController;


    public Main() {

        collection.add(
                new MusicBand(
                        1,
                        "A",
                        new Coordinates(1,2),
                        1,
                        1,
                        MusicGenre.PROGRESSIVE_ROCK,
                        new Person(
                                "A",
                                190L,
                                Color.WHITE,
                                Country.GERMANY,
                                new Location(1,
                                        2,
                                        "A"
                                )
                        )
                ));

//        Thread.sleep(200);

        collection.add(
                new MusicBand(
                        2,
                        "B",
                        new Coordinates(3,4),
                        2,
                        2,
                        MusicGenre.HIP_HOP,
                        new Person(
                                "B",
                                null,
                                null,
                                null,
                                new Location(3,
                                        2,
                                        "B"
                                )
                        )
                ));

        collection.add(
                new MusicBand(
                        3,
                        "C",
                        new Coordinates(5,6),
                        3,
                        3,
                        MusicGenre.POST_ROCK,
                        new Person(
                                "C",
                                195L,
                                Color.GREEN,
                                null,
                                new Location(5,
                                        6,
                                        "C"
                                )
                        )
                ));

        collection.add(
                new MusicBand(
                        4,
                        "D",
                        new Coordinates(7,8),
                        4,
                        4,
                        MusicGenre.POST_PUNK,
                        null
                ));
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage rootStage) {

        this.rootStage = rootStage;

        try {

            loadEditMusicBandDialogPane();
            loadLoginDialogPane();
            loadRootPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        loadCollectionOverviewPane();
//        initRootPane();

//        initLoginDialogPane();

        initCollectionOverviewPane();

//        initEditMusicBandDialogPane();
//        editMusicBandDialogController.setDialogStage(rootStage);
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

            rootStage.setResizable(false); // Is this really needed ?

            rootStage.setTitle("Authorisation/Registration");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initCollectionOverviewPane() {

        try {
            loadCollectionOverviewPane();

            collectionOverviewController.setCollectionOverviewStage(rootStage);

            Scene scene = new Scene(collectionOverviewPane);

            rootStage.setScene(scene);

            rootStage.setResizable(false); // Is this really needed ?

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


    private void initEditMusicBandDialogPane() {

        try {
            loadEditMusicBandDialogPane();

            Scene scene = new Scene(editMusicBandDialogPane);

            rootStage.setScene(scene);

            rootStage.setTitle("Create new music band.");
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

        rootPaneController = loader.getController();
        rootPaneController.setMain(this);

    }


    private void loadEditMusicBandDialogPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/EditMusicBandDialogPane.fxml"));
        editMusicBandDialogPane = loader.load();

        editMusicBandDialogController = loader.getController();
        editMusicBandDialogController.setMain(this);

    }


    private void loadLoginDialogPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/LoginDialogPane.fxml"));
        loginDialogPane = loader.load();

        loginDialogController = loader.getController();
        loginDialogController.setMain(this);

    }


    private void loadCollectionOverviewPane() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/CollectionOverviewPane.fxml"));
        collectionOverviewPane = loader.load();

        collectionOverviewController = loader.getController();
        collectionOverviewController.setMain(this);

    }



    // Getters and setters

    public Stage getRootStage() {
        return rootStage;
    }


    // Panes

    public BorderPane getRootPane() {
        return rootPane;
    }

    public AnchorPane getLoginDialogPane() {
        return loginDialogPane;
    }

    public AnchorPane getCollectionOverviewPane() {
        return collectionOverviewPane;
    }

    public BorderPane getEditMusicBandDialogPane() {
        return editMusicBandDialogPane;
    }


    // Controllers

    public RootPaneController getRootPaneController() {
        return rootPaneController;
    }

    public CollectionOverviewController getCollectionOverviewController() {
        return collectionOverviewController;
    }

    public EditMusicBandDialogController getEditMusicBandDialogController() {
        return editMusicBandDialogController;
    }

    public LoginDialogController getLoginDialogController() {
        return loginDialogController;
    }


    // Other staff

    public ObservableList<MusicBand> getCollection() {
        return collection;
    }
}
