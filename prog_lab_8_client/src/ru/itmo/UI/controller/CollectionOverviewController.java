package ru.itmo.UI.controller;

import javafx.beans.property.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itmo.UI.Main;
import ru.itmo.core.common.classes.Color;
import ru.itmo.core.common.classes.Country;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.classes.MusicGenre;

import java.util.Date;


public class CollectionOverviewController {


    private Main main;

    private Stage stage;


//----------------------------------------------------------------------------------------------------------------------


    private String nullSymbol = "null";

//----------------------------------------------------------------------------------------------------------------------


    @FXML
    private AnchorPane pane;

    @FXML
    private TextField filterField;

    @FXML
    private Button visualizeCollectionButton;

    @FXML
    private Button editChosenElementButton;


    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<String> userCommandComboBox;

    @FXML
    private TextField argumentField;

    @FXML
    private Button createMusicBandButton;

    @FXML
    private TextArea resultArea;

//----------------------------------------------------------------------------------------------------------------------


    @FXML
    private TableView<MusicBand> collectionTable;

    @FXML
    private TableColumn<MusicBand, Integer> idColumn;

    @FXML
    private TableColumn<MusicBand, String> nameColumn;

    @FXML
    private TableColumn<MusicBand, Double> coordXColumn;

    @FXML
    private TableColumn<MusicBand, Integer> coordYColumn;

    @FXML
    private TableColumn<MusicBand, Date> creationDateColumn;

    @FXML
    private TableColumn<MusicBand, Long> numberOfParticipantsColumn;

    @FXML
    private TableColumn<MusicBand, Integer> singlesCountColumn;

    @FXML
    private TableColumn<MusicBand, MusicGenre> musicGenreColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManNameColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManHeightColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManHeirColorColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManNationalityColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManLocXColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManLocYColumn;

    @FXML
    private TableColumn<MusicBand, Object> frontManLocNameColumn;


//----------------------------------------------------------------------------------------------------------------------


    // Some other fields


    private boolean visualizeCollectionButtonWasPressed;
//    private boolean editMusicBandDialogStageIsCreated
//            = false;

//    private Stage editMusicBandBdDialogStage;

//    @FXML
//    private TableColumn<MusicBand, >

    ///

    ///

    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setTitle("Collection overview.");
        stage.setResizable(false);


        initElements();

    }


    private void initialiseAfterMainSet() {

        fillCollectionTable();

        BorderPane rootPane = main.getRootPane();
        rootPane.setCenter(pane);

        Scene scene = new Scene(rootPane);
        stage.setScene(scene);

    }


    // TODO: 8/18/20
    private void initElements() {

        initCollectionTable();
        initComboBoxes(); // TODO: 8/18/20
    }


    private void initCollectionTable() {

        idColumn.setCellValueFactory(
                cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject()


        );

        nameColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(cellData.getValue().getName())
        );

        coordXColumn.setCellValueFactory(
                cellData ->
                        new SimpleDoubleProperty(cellData.getValue().getCoordinates().getX()).asObject()
        );

        coordYColumn.setCellValueFactory(
                cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getCoordinates().getY()).asObject()
        );

        creationDateColumn.setCellValueFactory(
                cellData ->
                        new SimpleObjectProperty<>(cellData.getValue().getCreationDate())
        );

        numberOfParticipantsColumn.setCellValueFactory(
                cellData ->
                        new SimpleLongProperty(cellData.getValue().getNumberOfParticipants()).asObject()
        );

        singlesCountColumn.setCellValueFactory(
                cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getSinglesCount()).asObject()
        );

        musicGenreColumn.setCellValueFactory(
                cellData ->
                        new SimpleObjectProperty<>(cellData.getValue().getGenre())
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManNameColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                                ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getName())
                                : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManNameColumn.setComparator(
                (name, otherName) -> {
                    if (name.equals(nullSymbol)) return -1;
                    else if (otherName.equals(nullSymbol)) return 1;
                    else return ((String) name).compareTo((String) otherName);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManHeightColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                                ?
                                (cellData.getValue().getFrontMan().getHeight() != null)
                                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getHeight())
                                        : new SimpleObjectProperty<>(nullSymbol)
                                : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManHeightColumn.setComparator(
                (height, otherHeight) -> {
                    if (height.equals(nullSymbol)) return -1;
                    else if (otherHeight.equals(nullSymbol)) return 1;
                    else return ((Long) height).compareTo((Long) otherHeight);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManHeirColorColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                                ?
                                (cellData.getValue().getFrontMan().getHeirColor() != null)
                                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getHeirColor())
                                        : new SimpleObjectProperty<>(nullSymbol)
                                : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManHeirColorColumn.setComparator(
                (color, otherColor) -> {
                    if (color.equals(nullSymbol)) return -1;
                    else if (otherColor.equals(nullSymbol)) return 1;
                    else return ((Color) color).compareTo((Color) otherColor);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManNationalityColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                                ?
                                (cellData.getValue().getFrontMan().getNationality() != null)
                                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getNationality())
                                        : new SimpleObjectProperty<>(nullSymbol)
                                : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManNationalityColumn.setComparator(
                (country, otherCountry) -> {
                    if (country.equals(nullSymbol)) return -1;
                    else if (otherCountry.equals(nullSymbol)) return 1;
                    else return ((Country) country).compareTo((Country) otherCountry);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocXColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                                ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getX())
                                : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManLocXColumn.setComparator(
                (locX, otherLocX) -> {
                    if (locX.equals(nullSymbol)) return -1;
                    else if (otherLocX.equals(nullSymbol)) return 1;
                    else return ((Integer) locX).compareTo((Integer) otherLocX);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocYColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getY())
                        : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManLocYColumn.setComparator(
                (locY, otherLocY) -> {
                    if (locY.equals(nullSymbol)) return -1;
                    else if (otherLocY.equals(nullSymbol)) return 1;
                    else return ((Integer) locY).compareTo((Integer) otherLocY);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocNameColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getName())
                        : new SimpleObjectProperty<>(nullSymbol)
        );

        frontManLocNameColumn.setComparator(
                (name, otherName) -> {
                    if (name.equals(nullSymbol)) return -1;
                    else if (otherName.equals(nullSymbol)) return 1;
                    else return ((String) name).compareTo((String) otherName);
                }
        );

//----------------------------------------------------------------------------------------------------------------------

    }
    
    
    private void fillCollectionTable() {
        
        connectTableViewToCollection();

        enableFiltering();
    }
    
    
    private void initComboBoxes() {
        // TODO: 8/18/20  
    }


    @FXML
    private void handleCreateMusicBandButton() {

        EditMusicBandDialogController editMusicBandDialogController = main.loadEditMusicBandDialog();
        Stage stage = editMusicBandDialogController.getStage(); // -- everything sweet begins here

        stage.setTitle("Create MusicBand");
        stage.initOwner(this.stage);

       editMusicBandDialogController.setMusicBand(null);

        stage.showAndWait();
        //TODO
        // Our MusicBand :
         System.out.println(editMusicBandDialogController.getMusicBand());
    }


    @FXML
    private void handleVisualizeCollection() { // // TODO: 8/18/20 Some mistakes could be here 

        main.getCollectionVisualizationController().getStage().show();
//        stage.show();

    }


    private void initUserCommandComboBox() {
        //TODO
    }


    private void handleSubmitButton() {
        //TODO
    }


    private void connectTableViewToCollection() {

        collectionTable.setItems(main.getCollection());

    }


    private void enableFiltering() {
        FilteredList<MusicBand> collectionFiltered = new FilteredList<>(main.getCollection(), p -> true);

        filterField.textProperty().addListener(
                (observable, oldValue, newValue) ->
                    collectionFiltered.setPredicate(
                            musicBand -> {
                                if (newValue == null || newValue.isEmpty()) return true;

                                String filterTextLowerCase = newValue.toLowerCase();

                                if (musicBand.getId().toString().toLowerCase().contains(filterTextLowerCase)) return true;

                                if (musicBand.getName().toLowerCase().contains(filterTextLowerCase)) return true;

                                if (String.valueOf(musicBand.getCoordinates().getX()).toLowerCase().contains(filterTextLowerCase)) return true;

                                if (String.valueOf(musicBand.getCoordinates().getY()).toLowerCase().contains(filterTextLowerCase)) return true;

                                if (musicBand.getCreationDate().toString().toLowerCase().contains(filterTextLowerCase)) return true;

                                if (String.valueOf(musicBand.getNumberOfParticipants()).toLowerCase().contains(filterTextLowerCase)) return true;

                                if (String.valueOf(musicBand.getSinglesCount()).toLowerCase().contains(filterTextLowerCase)) return true;

                                if (musicBand.getGenre().toString().toLowerCase().contains(filterTextLowerCase)) return true;


                                if (musicBand.getFrontMan() == null && nullSymbol.toLowerCase().contains(filterTextLowerCase)) return true;

                                if (musicBand.getFrontMan() != null) {


                                    if (musicBand.getFrontMan().getName().toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (musicBand.getFrontMan().getHeight() == null && nullSymbol.toLowerCase().contains(filterTextLowerCase)) return true;

                                    if (musicBand.getFrontMan().getHeight() != null && musicBand.getFrontMan().getHeight().toString().toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (musicBand.getFrontMan().getHeirColor() == null && nullSymbol.toLowerCase().contains(filterTextLowerCase)) return true;

                                    if (musicBand.getFrontMan().getHeirColor() != null && musicBand.getFrontMan().getHeirColor().toString().toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (musicBand.getFrontMan().getNationality() == null && nullSymbol.toLowerCase().contains(filterTextLowerCase)) return true;

                                    if (musicBand.getFrontMan().getNationality() != null && musicBand.getFrontMan().getNationality().toString().toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (musicBand.getFrontMan().getLocation().getX().toString().toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (String.valueOf(musicBand.getFrontMan().getLocation().getY()).toLowerCase().contains(filterTextLowerCase)) return true;


                                    if (musicBand.getFrontMan().getLocation().getName().toLowerCase().contains(filterTextLowerCase)) return true;

                                }


                                return false;

                            }
                    )
        );

        SortedList<MusicBand> collectionSorted = new SortedList<>(collectionFiltered);

        // 4. Bind the SortedList comparator to the TableView comparator.
        collectionSorted.comparatorProperty().bind(collectionTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        collectionTable.setItems(collectionSorted);

    }


    // Getters and setters

    public void setMain(Main main) {

        this.main = main;

        initialiseAfterMainSet();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public String getNullSymbol() {
        return nullSymbol;
    }

    public void setNullSymbol(String nullSymbol) {
        this.nullSymbol = nullSymbol;
    }

}
