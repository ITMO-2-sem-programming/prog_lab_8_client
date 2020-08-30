package ru.itmo.UI.controller;

import javafx.beans.property.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import ru.itmo.UI.Main;
import ru.itmo.core.command.representation.*;
import ru.itmo.core.common.classes.Color;
import ru.itmo.core.common.classes.Country;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.classes.MusicGenre;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadCollectionServiceRequest;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadOwnedElementsServiceRequest;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.*;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class CollectionOverviewController
        implements Localizable {


    private Main main;

    private Stage stage;

    private Locale locale;

    private NumberFormat numberFormatter
            = NumberFormat.getNumberInstance();

    private String dateFormatPattern
            = "yyyy-MM-dd HH:mm:ss";

    private DateFormat dateFormatter
            = DateFormat.getDateTimeInstance();



//----------------------------------------------------------------------------------------------------------------------


    private String nullSymbol = "null";

//----------------------------------------------------------------------------------------------------------------------


    // CollectionOverview elements


    @FXML
    private AnchorPane pane;

    @FXML
    private Label filterTableLabel;

    @FXML
    private Label commandLabel;

    @FXML
    private Label argumentLabel;

    @FXML
    private Label elementLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private TextField filterField;

    @FXML
    private Button visualizeCollectionButton;

    @FXML
    private Button editChosenElementButton;

    @FXML
    private Button removeChosenElementButton;


    @FXML
    private Button submitButton;

    @FXML
    private ComboBox<CommandRepresentation> userCommandComboBox;

    @FXML
    private TextField argumentField;

    @FXML
    private Button createMusicBandButton;

    @FXML
    private TextArea resultArea;


//----------------------------------------------------------------------------------------------------------------------


    // TableView section


    @FXML
    private TableView<MusicBand> collectionTable;

    @FXML
    private TableColumn<MusicBand, String> idColumn;

    @FXML
    private TableColumn<MusicBand, String> nameColumn;

    @FXML
    private TableColumn<MusicBand, String> coordXColumn;

    @FXML
    private TableColumn<MusicBand, Integer> coordYColumn;

    @FXML
    private TableColumn<MusicBand, String> creationDateColumn;

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


    // Methods


    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setTitle("Collection overview.");
        stage.setResizable(false);

//        stage.setOnShowing(
//                (event) -> {
//                    loadCollection();
//                    loadOwnedElementsID();
//                }
//        );

        initElements();



    }


    private void initializeAfterMainSet() {

        fillCollectionTable();

        BorderPane rootPane = main.getRootPane();
        rootPane.setCenter(pane);

        Scene scene = new Scene(rootPane);
        stage.setScene(scene);

    }


    private void initElements() {

        initCollectionTable();
        initComboBoxes();
    }


    private void initCollectionTable() {

        idColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(numberFormatter.format(cellData.getValue().getId())) // TODO: 30.08.2020 Changes here


        );

        //// TODO: 30.08.2020  Is it really needed HERE ?
        idColumn.setComparator(
                (id, otherId) -> {
                    Integer integerId;
                    Integer integerOtherId;

                    if (id.equals("null")) {
                        integerId = null;
                    } else {
                        integerId = Integer.parseInt(id);
                    }

                    if (otherId.equals("null")) {
                        integerOtherId = null;
                    } else {
                        integerOtherId = Integer.parseInt(otherId);
                    }

                    if (integerId == null) {
                        return -1;
                    } else if (integerOtherId == null) {
                        return 1;
                    } else
                        return Integer.compare(integerId, integerOtherId); // TODO: 30.08.2020 Javafx cell factory base comparator
                }
        );

        nameColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(cellData.getValue().getName())
        );

        coordXColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(numberFormatter.format(cellData.getValue().getCoordinates().getX()))
        );

        coordXColumn.setComparator(
                (coordX, otherCoordX) -> {
                    double coordXDouble;
                    double otherCoordXDouble;
                    try {
                        coordXDouble = numberFormatter.parse(coordX).doubleValue();
                        otherCoordXDouble = numberFormatter.parse(otherCoordX).doubleValue();

                        return Double.compare(coordXDouble, otherCoordXDouble);


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return 0;
                }
        );

        coordYColumn.setCellValueFactory(
                cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getCoordinates().getY()).asObject()
        );

        creationDateColumn.setCellValueFactory( // TODO: 30.08.2020 String -> Date
                cellData ->
                        new SimpleStringProperty(dateFormatter.format(cellData.getValue().getCreationDate()))
        );

        creationDateColumn.setComparator(

                (date, otherDate) -> {
                    Date dateCreationDate;
                    Date dateOtherCreationDate;


                    try {
                        dateCreationDate = dateFormatter.parse(date);
                        dateOtherCreationDate = dateFormatter.parse(otherDate);
                        return dateCreationDate.compareTo(dateOtherCreationDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return 0;
                }
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
        initUserCommandComboBox();
    }


    private void initUserCommandComboBox() {
        userCommandComboBox.getItems().addAll(
                new UpdateCommandRepresentation(),
                new ShowCommandRepresentation(),
                new ReplaceIfLowerCommandRepresentation(),
                new RemoveLowerKeyCommandRepresentation(),
                new RemoveGreaterCommandRepresentation(),
                new RemoveByKeyCommandRepresentation(),
                new RemoveAllByGenreCommandRepresentation(),
                new MaxByFrontManCommandRepresentation(),
                new InsertCommandRepresentation(),
                new InfoCommandRepresentation(),
                new HelpCommandRepresentation(),
                new FilterGreaterThanSinglesCountCommandRepresentation(),
//                new ExecuteScriptCommandRepresentation(),
                new ClearCommandRepresentation()
        );
    }


    @FXML
    private void handleVisualizeCollection() {

//        Stage stage = main.loadCollectionVisualization().getStage();
        Stage stage = main.loadCollectionVisualization().getStage();
        stage.show();

    }


    @FXML
    private void handleEditChosenElementButton() {

        MusicBand selectedElement = collectionTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(this.stage);


        if (selectedElement == null) {
            alert.setTitle("No selection");
            alert.setHeaderText("No element selected");
            alert.setContentText("Please select an element in the table");

            alert.showAndWait();

        } else {

            if ( ! main.getOwnedElementsID().contains(selectedElement.getId())) {

                alert.setHeaderText("Impossible operation");
                alert.setContentText("You can't edit chosen element as you don't own it.");

                alert.showAndWait();

            } else {

                EditMusicBandDialogController editMusicBandDialogController = main.loadEditMusicBandDialog();
                editMusicBandDialogController.setMusicBand(selectedElement);
                Stage stage = editMusicBandDialogController.getStage();
                stage.initOwner(this.stage);

                stage.showAndWait();

                MusicBand newMusicBand = editMusicBandDialogController.getMusicBand();

                if ( newMusicBand != null ) {
                    main.getClient().addRequest(
                            new UpdateCommandRequest(newMusicBand.getId(), newMusicBand));

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }

                // TODO: 28.08.2020
//                GeneralResponse response = main.getClient().getUserCommandResponse();
//
//                if (response.errorOccurred()) {
//                    Alert alert = new Alert(AlertType.ERROR);
//                    alert.initOwner(this.stage);
//                    alert.setTitle("Incorrect data");
//                    alert.setHeaderText("Impossible operation");
//                    alert.setContentText(response.getMessage());
//
//                    alert.showAndWait();
//                }
            }
        }
    }


    @FXML
    private void handleRemoveChosenElementButton() {

        MusicBand selectedElement = collectionTable.getSelectionModel().getSelectedItem();

        if (selectedElement == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(this.stage);
            alert.setTitle("No selection");
            alert.setHeaderText("No element selected");
            alert.setContentText("Please select an element in the table");

            alert.showAndWait();

        } else {

            if ( ! main.getOwnedElementsID().contains(selectedElement.getId())) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(this.stage);
                alert.setTitle("Incorrect data");
                alert.setHeaderText("Impossible operation");
                alert.setContentText("You can't edit chosen element as you don't own it.");

                alert.showAndWait();

            } else {

                main.getClient().addRequest(new RemoveByKeyCommandRequest(selectedElement.getId()));

                GeneralResponse response = main.getClient().getUserCommandResponse();

                if (response.errorOccurred()) {
                    new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
                } else {
                    resultArea.setText(response.getMessage());
                }
            }
        }
    }


    @FXML
    private void handleUserCommandComboBox() {

        CommandRepresentation cmdRepr = userCommandComboBox.getValue();

        argumentField.setDisable( ! cmdRepr.hasSimpleArgument());
        createMusicBandButton.setDisable( ! cmdRepr.hasElementArgument());

        resultArea.setText(cmdRepr.getCommandDescription());

        if (main.getEditMusicBandDialogController() != null) {
            main.getEditMusicBandDialogController().setMusicBand(null);
        }

    }


    @FXML
    private void handleCreateMusicBandButton() {

        EditMusicBandDialogController editMusicBandDialogController = main.loadEditMusicBandDialog();
        Stage stage = editMusicBandDialogController.getStage(); // -- everything sweet begins here

        stage.setTitle("Create MusicBand");
        stage.initOwner(this.stage);

       editMusicBandDialogController.setMusicBand(null);

        stage.showAndWait();

    }


    @FXML
    private void handleSubmitButton() {


        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Incorrect data");
        alert.setHeaderText("Incorrect argument");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);


        CommandRepresentation cmdRepr = userCommandComboBox.getValue();

        if (cmdRepr == null) return;

        if (cmdRepr instanceof UpdateCommandRepresentation) { // TODO: 28.08.2020
            UpdateCommandRequest updateCommandRequest = null;
            int ID;
            MusicBand newMusicBand;
            try {
                ID  = Integer.parseInt(argumentField.getText());
                newMusicBand = main.getEditMusicBandDialogController().getMusicBand();

                if (newMusicBand != null) {
                    newMusicBand.setId(ID);
                }


                updateCommandRequest
                        = new UpdateCommandRequest(
                                ID,
                                newMusicBand
                        );

                if ( ! main.getOwnedElementsID().contains(updateCommandRequest.getID()) ) {
                    alert.setContentText(
                            String.format(
                                    "You can't update element with ID : '%s' as you don't own it.",
                                    updateCommandRequest.getID()
                            )
                    );
                }

            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } catch (NullPointerException e) {
                alert.setContentText("Element can't be null.");
            } finally {
                if ( ! alert.getContentText().equals("") )
                    alert.showAndWait();
                else {
                    main.getClient().addRequest(updateCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof ShowCommandRepresentation) {
            main.getClient().addRequest(new ShowCommandRequest());

            GeneralResponse response = main.getClient().getUserCommandResponse();
            if (response.errorOccurred()) {
                new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
            } else {
                resultArea.setText(response.getMessage());
            }

        } else if (cmdRepr instanceof ReplaceIfLowerCommandRepresentation) { // TODO: 28.08.2020

            ReplaceIfLowerCommandRequest request = null;

            int ID;
            MusicBand newMusicBand;
            try {
                ID =  Integer.parseInt(argumentField.getText());
                newMusicBand = main.getEditMusicBandDialogController().getMusicBand();

                if (newMusicBand != null) {
                    newMusicBand.setId(ID);
                }


                if ( ! main.getOwnedElementsID().contains(ID)) {
                    alert.setContentText(
                            String.format(
                                    "You can't replace the element with ID : '%s' as you don't own it.",
                                    ID
                            )
                    );
                }

                request = new ReplaceIfLowerCommandRequest(ID,newMusicBand);

            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } catch (NullPointerException e) {
                alert.setContentText("Element can't be 'null'.");
            } finally {
                if ( ! alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(request);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof RemoveLowerKeyCommandRepresentation) { // TODO: 28.08.2020
            RemoveLowerKeyCommandRequest removeLowerKeyCommandRequest = null;
            try {
                removeLowerKeyCommandRequest
                        = new RemoveLowerKeyCommandRequest(
                        Integer.parseInt(argumentField.getText())
                );
            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(removeLowerKeyCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof RemoveGreaterCommandRepresentation) { // TODO: 28.08.2020
            RemoveGreaterCommandRequest removeGreaterCommandRequest = null;
            try {
                removeGreaterCommandRequest
                        = new RemoveGreaterCommandRequest(
                        main.getEditMusicBandDialogController().getMusicBand()
                );
            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } catch (NullPointerException e) {
                alert.setContentText("Element can't be null.");
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(removeGreaterCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof RemoveByKeyCommandRepresentation) { // TODO: 28.08.2020
            RemoveByKeyCommandRequest removeByKeyCommandRequest = null;
            try {
                removeByKeyCommandRequest
                        = new RemoveByKeyCommandRequest(
                        Integer.parseInt(argumentField.getText())
                );

                if ( ! main.getOwnedElementsID().contains(removeByKeyCommandRequest.getID())) {
                    alert.setContentText(
                            String.format(
                                    "You can't remove element with ID : '%s' as you don't own it.",
                                    removeByKeyCommandRequest.getID()
                            )
                    );
                }

            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(removeByKeyCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof RemoveAllByGenreCommandRepresentation) { // TODO: 28.08.2020
            RemoveAllByGenreCommandRequest removeAllByGenreCommandRequest = null;
            try {
                removeAllByGenreCommandRequest
                        = new RemoveAllByGenreCommandRequest(
                        MusicGenre.valueOf(argumentField.getText().toUpperCase())
                );
            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(MusicBand.musicBandFieldsDescription.get("genre"));
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(removeAllByGenreCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof MaxByFrontManCommandRepresentation) {
            main.getClient().addRequest(new MaxByFrontManCommandRequest());

            GeneralResponse response = main.getClient().getUserCommandResponse();
            if (response.errorOccurred()) {
                new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
            } else {
                resultArea.setText(response.getMessage());
            }


        } else if (cmdRepr instanceof InsertCommandRepresentation) { // TODO: 28.08.2020
            InsertCommandRequest insertCommandRequest = null;

            MusicBand newMusicBand = null;
            try {
                newMusicBand = main.getEditMusicBandDialogController().getMusicBand();
                insertCommandRequest
                        = new InsertCommandRequest(
                        newMusicBand
                );
            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } catch (NullPointerException e) {
                alert.setContentText("Element can't be null.");
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(insertCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        alert.setContentText(response.getMessage());
                        alert.showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

        } else if (cmdRepr instanceof InfoCommandRepresentation) {
            main.getClient().addRequest(new InfoCommandRequest());

            GeneralResponse response = main.getClient().getUserCommandResponse();
            if (response.errorOccurred()) {
                new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
            } else {
                resultArea.setText(response.getMessage());
            }

        } else if (cmdRepr instanceof HelpCommandRepresentation) {
            main.getClient().addRequest(new HelpCommandRequest());

            GeneralResponse response = main.getClient().getUserCommandResponse();
            if (response.errorOccurred()) {
                System.out.println("sdfsfdsf");
                new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
            } else {
                System.out.println("dffffffff");
                resultArea.setText(response.getMessage());
            }

        } else if (cmdRepr instanceof FilterGreaterThanSinglesCountCommandRepresentation) {
            FilterGreaterThanSinglesCountCommandRequest filterGreaterThanSinglesCountCommandRequest = null;
            try {
                filterGreaterThanSinglesCountCommandRequest
                        = new FilterGreaterThanSinglesCountCommandRequest(
                        Integer.parseInt(argumentField.getText())
                );
            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } finally {
                if (!alert.getContentText().equals(""))
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(filterGreaterThanSinglesCountCommandRequest);

                    GeneralResponse response = main.getClient().getUserCommandResponse();

                    if (response.errorOccurred()) {
                        new Alert(AlertType.ERROR, response.getMessage()).showAndWait();
                    } else {
                        resultArea.setText(response.getMessage());
                    }
                }
            }

//        } else if (cmdRepr instanceof ExecuteScriptCommandRepresentation) {
//            ExecuteScriptCommand executeScriptCommand = null;
//            try {
//                executeScriptCommand
//                        = new ExecuteScriptCommand(
//                        argumentField.getText()
//                );
//            } catch (NumberFormatException e) {
//                alert.setContentText("Argument must be integer.");
//            } catch (IllegalArgumentException e) {
//                alert.setContentText(e.getMessage());
//            } finally {
//                if (!alert.getContentText().equals(""))
//                    alert.showAndWait();
//                else {
//                    System.out.println("blblbla");
//                    main.getClient().addRequest(new Request(executeScriptCommand));
//                }
//            }
        } else if (cmdRepr instanceof ClearCommandRepresentation) {
            main.getClient().addRequest(new ClearCommandRequest());

            GeneralResponse response = main.getClient().getUserCommandResponse();

            if (response.errorOccurred()) {
                alert.setContentText(response.getMessage());
                alert.showAndWait();
            } else {
                resultArea.setText(response.getMessage());
            }
        }
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


    @Override
    public void localize(Locale locale) {
        
        numberFormatter = NumberFormat.getInstance(locale);
        dateFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale);

        initCollectionTable();

        ResourceBundle localeBundle = ResourceBundle.getBundle(
                main.getBaseResourceBundle().getBaseBundleName(),
                locale);

        filterTableLabel.setText(localeBundle.getString("label.filterTableLabel.text"));
        commandLabel.setText(localeBundle.getString("label.commandLabel.text"));
        argumentLabel.setText(localeBundle.getString("label.argumentLabel.text"));
        elementLabel.setText(localeBundle.getString("label.elementLabel.text"));
        resultLabel.setText(localeBundle.getString("label.resultLabel.text"));

        filterField.setPromptText(localeBundle.getString("textField.filterField.promptText"));

        visualizeCollectionButton.setText(localeBundle.getString("button.visualizeCollectionButton.text"));
        editChosenElementButton.setText(localeBundle.getString("button.editChosenElementButton.text"));
        removeChosenElementButton.setText(localeBundle.getString("button.removeChosenElementButton.text"));
        submitButton.setText(localeBundle.getString("button.submitButton.text"));
        createMusicBandButton.setText(localeBundle.getString("button.createMusicBandButton.text"));



// TODO: 29.08.2020  
    }


//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters


    public void setMain(Main main) {

        this.main = main;

        initializeAfterMainSet();
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
