package ru.itmo.UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import resourses.locale.locale;
import ru.itmo.UI.controller.*;
import ru.itmo.core.common.classes.*;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadCollectionServiceRequest;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadOwnedElementsServiceRequest;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;


public class Main extends Application {


    Client client;
    
    private final ResourceBundle baseResourceBundle
            = ResourceBundle.getBundle("resourses.locale.locale");


    private ObservableList<MusicBand> collection
            = FXCollections.observableArrayList();

    private ObservableList<Integer> ownedElementsID
            = FXCollections.observableArrayList();


    private Stage rootStage;
    private BorderPane rootPane;

//    private AnchorPane loginDialogPane;
//    private AnchorPane collectionOverviewPane;
//    private BorderPane editMusicBandDialogPane;
//    private AnchorPane collectionVisualizationPane;

    private RootPaneController rootPaneController;
    private CollectionOverviewController collectionOverviewController;
    private CollectionVisualizationController collectionVisualizationController;
    private EditMusicBandDialogController editMusicBandDialogController;
    private LoginDialogController loginDialogController;


    public Main() {


        client = new Client(this, "localhost", 44321); // TODO: 20.08.2020 Some checks here

        client.start();

//        ownedElementsID.addAll(3,4);
//
//        collection.add(
//                new MusicBand(
//                        1,
//                        "A",
//                        new Coordinates(1,2),
//                        1,
//                        1,
//                        MusicGenre.PROGRESSIVE_ROCK,
//                        new Person(
//                                "A",
//                                190L,
//                                Color.WHITE,
//                                Country.GERMANY,
//                                new Location(1,
//                                        2,
//                                        "A"
//                                )
//                        )
//                ));
//
////        Thread.sleep(200);
//
//        collection.add(
//                new MusicBand(
//                        2,
//                        "B",
//                        new Coordinates(3,4),
//                        2,
//                        2,
//                        MusicGenre.HIP_HOP,
//                        new Person(
//                                "B",
//                                null,
//                                null,
//                                null,
//                                new Location(3,
//                                        2,
//                                        "B"
//                                )
//                        )
//                ));
//
//        collection.add(
//                new MusicBand(
//                        3,
//                        "C",
//                        new Coordinates(5,6),
//                        3,
//                        3,
//                        MusicGenre.POST_ROCK,
//                        new Person(
//                                "C",
//                                195L,
//                                Color.GREEN,
//                                null,
//                                new Location(5,
//                                        6,
//                                        "C"
//                                )
//                        )
//                ));
//
//        collection.add(
//                new MusicBand(
//                        4,
//                        "D",
//                        new Coordinates(7,8),
//                        4,
//                        4,
//                        MusicGenre.POST_PUNK,
//                        null
//                ));
    }


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage rootStage) {
//        initialize();

        this.rootStage = rootStage;

        try {

            loadLoginDialog();
            loadRoot();
//            loadCollectionOverview();
//            loadEditMusicBandDialog();
//            loadCollectionVisualization();


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
//        loadCollectionOverviewPane();
//        initRootPane();

//        initLoginDialogPane();

//        initCollectionOverviewPane();
        loginDialogController.getStage().show();

//        initEditMusicBandDialogPane();
//        editMusicBandDialogController.setDialogStage(rootStage);
//        rootStage.show();
    }


    // Разобраться с этим позднее
//    private void initialize() {
//        collection = FXCollections.observableArrayList();
//
//    }


    /**
     * Creates a scene with LoginDialogPane and sets it to rootStage.
     * <br>Sets title of the stage.
     */
//    private void initLoginDialogPane() {
//
//        try {
//            loadLoginDialogPane();
//
//            Scene scene = new Scene(loginDialogPane);
//
//            rootStage.setScene(scene);
//
//            rootStage.setResizable(false); // Is this really needed ?
//
//            rootStage.setTitle("Authorisation/Registration");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


//    private void initCollectionOverviewPane() {
//
//        try {
//            loadCollectionOverviewPane();
//
//            collectionOverviewController.setCollectionOverviewStage(rootStage);
//
//            Scene scene = new Scene(collectionOverviewPane);
//
//            rootStage.setScene(scene);
//
//            rootStage.setResizable(false); // Is this really needed ?
//
//            rootStage.setTitle("CollectionOverview");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



//    private void initRootPane() {
//
//        try {
//            loadRootPane();
//
//            Scene scene = new Scene(rootPane);
//
//            rootStage.setScene(scene);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    private void loadLoginDialog() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/LoginDialogPane.fxml"));
            loader.load();

            loginDialogController = loader.getController();
            loginDialogController.setMain(this);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }


    }


    private RootPaneController loadRoot() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootPane.fxml"));
            rootPane = loader.load();

            rootPaneController = loader.getController();
            rootPaneController.setMain(this);

            return rootPaneController;

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    // Отличается от остальных методов загрузки, так как EditMusicBandDialog может быть вызван из разных окон,
    // а устанавливать окно-владельца можно только один раз для конкретного экземпляра.
    public EditMusicBandDialogController loadEditMusicBandDialog() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditMusicBandDialogPane.fxml"));
            loader.load();

            editMusicBandDialogController = loader.getController();
            editMusicBandDialogController.setMain(this);

            return editMusicBandDialogController;

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    public CollectionOverviewController loadCollectionOverview() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CollectionOverviewPane.fxml"));
            loader.load();

            collectionOverviewController = loader.getController();
            collectionOverviewController.setMain(this);

            return collectionOverviewController;

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }


    public CollectionVisualizationController loadCollectionVisualization() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CollectionVisualizationPane.fxml"));
            loader.load();

            collectionVisualizationController = loader.getController();
            collectionVisualizationController.setMain(this);

            return collectionVisualizationController;

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public void loadCollection() {
        client.addRequest(
                new LoadCollectionServiceRequest()
        );
    }

    public void loadOwnedElementsID() {
        client.addRequest(
                new LoadOwnedElementsServiceRequest()
        );
    }


//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public Client getClient() {
        return client;
    }


    public Stage getRootStage() {
        return rootStage;
    }


    // Panes


    public BorderPane getRootPane() {
        return rootPane;
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

    public CollectionVisualizationController getCollectionVisualizationController() {
        return collectionVisualizationController;
    }


    // Other staff


    public ResourceBundle getBaseResourceBundle() {
        return baseResourceBundle;
    }

    public ObservableList<MusicBand> getCollection() {
        return collection;
    }

    public void setCollection(List<MusicBand> collection) {
        this.collection = FXCollections.observableArrayList(collection);
    }

    public ObservableList<Integer> getOwnedElementsID() {
        return ownedElementsID;
    }

    public void setOwnedElementsID(ObservableList<Integer> ownedElementsID) {
        this.ownedElementsID = ownedElementsID;
    }
}
