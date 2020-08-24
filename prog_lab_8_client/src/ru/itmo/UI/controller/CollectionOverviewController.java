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
import ru.itmo.core.common.exchange.request.clientCommand.userCommand.*;
import ru.itmo.core.common.exchange.request.Request;

import java.util.Date;


public class CollectionOverviewController {


    private Main main;

    private Stage stage;


//----------------------------------------------------------------------------------------------------------------------


    private String nullSymbol = "null";

//----------------------------------------------------------------------------------------------------------------------


    // CollectionOverview elements


    @FXML
    private AnchorPane pane;

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


    // Methods


    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setTitle("Collection overview.");
        stage.setResizable(false);


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
                new ExecuteScriptCommandRepresentation(),
                new ClearCommandRepresentation()
        );
    }


    @FXML
    private void handleVisualizeCollection() {

        Stage stage = main.getCollectionVisualizationController().getStage();
        stage.show();

    }


    @FXML
    private void handleEditChosenElement() {

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

                EditMusicBandDialogController editMusicBandDialogController = main.loadEditMusicBandDialog();
                editMusicBandDialogController.setMusicBand(selectedElement);
                Stage stage = editMusicBandDialogController.getStage();
                stage.initOwner(this.stage);

                stage.showAndWait();

                if ( ! (editMusicBandDialogController.getMusicBand() == null) ) {
                    main.getClient().addRequest(new Request(new UpdateCommand(selectedElement.getId(), selectedElement)));
                }
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

                main.getClient().addRequest(new Request(new RemoveByKeyCommand(selectedElement.getId())));
            }
        }
    }


    @FXML
    private void handleUserCommandComboBox() {

        CommandRepresentation cmdRepr = userCommandComboBox.getValue();

        argumentField.setDisable( ! cmdRepr.hasSimpleArgument());
        createMusicBandButton.setDisable( ! cmdRepr.hasElementArgument());

        resultArea.setText(cmdRepr.getCommandDescription());

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

        if (cmdRepr instanceof UpdateCommandRepresentation) {
            UpdateCommand updateCommand = null;
            try {
                updateCommand
                        = new UpdateCommand(
                                Integer.parseInt(argumentField.getText()),
                                main.getEditMusicBandDialogController().getMusicBand()
                        );

                if ( ! main.getOwnedElementsID().contains(updateCommand.getID()) ) {
                    alert.setContentText(
                            String.format(
                                    "You can't update element with ID : '%s' as you don't own it.",
                                    updateCommand.getID()
                            )
                    );
                }

            } catch (NumberFormatException e) {
                alert.setContentText("Argument must be integer.");
            } catch (IllegalArgumentException e) {
                alert.setContentText(e.getMessage());
            } finally {
                if ( ! alert.getContentText().equals("") )
                    alert.showAndWait();
                else {
                    System.out.println("blblbla");
                    main.getClient().addRequest(new Request(updateCommand));
                }
            }

        } else if (cmdRepr instanceof ShowCommandRepresentation) {
            main.getClient().addRequest(new Request(new ShowCommand()));

        } else if (cmdRepr instanceof ReplaceIfLowerCommandRepresentation) {
            ReplaceIfLowerCommand replaceIfLowerCommand = null;
            try {
                replaceIfLowerCommand
                        = new ReplaceIfLowerCommand(
                        Integer.parseInt(argumentField.getText()),
                        main.getEditMusicBandDialogController().getMusicBand()
                );

                if ( ! main.getOwnedElementsID().contains(replaceIfLowerCommand.getID())) {
                    alert.setContentText(
                            String.format(
                                    "You can't replace the element with ID : '%s' as you don't own it.",
                                    replaceIfLowerCommand.getID()
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
                    main.getClient().addRequest(new Request(replaceIfLowerCommand));
                }
            }

        } else if (cmdRepr instanceof RemoveLowerKeyCommandRepresentation) {
            RemoveLowerKeyCommand removeLowerKeyCommand = null;
            try {
                removeLowerKeyCommand
                        = new RemoveLowerKeyCommand(
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
                    main.getClient().addRequest(new Request(removeLowerKeyCommand));
                }
            }

        } else if (cmdRepr instanceof RemoveGreaterCommandRepresentation) {
            RemoveGreaterCommand removeGreaterCommand = null;
            try {
                removeGreaterCommand
                        = new RemoveGreaterCommand(
                        main.getEditMusicBandDialogController().getMusicBand()
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
                    main.getClient().addRequest(new Request(removeGreaterCommand));
                }
            }

        } else if (cmdRepr instanceof RemoveByKeyCommandRepresentation) {
            RemoveByKeyCommand removeByKeyCommand = null;
            try {
                removeByKeyCommand
                        = new RemoveByKeyCommand(
                        Integer.parseInt(argumentField.getText())
                );

                if ( ! main.getOwnedElementsID().contains(removeByKeyCommand.getID())) {
                    alert.setContentText(
                            String.format(
                                    "You can't remove element with ID : '%s' as you don't own it.",
                                    removeByKeyCommand.getID()
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
                    main.getClient().addRequest(new Request(removeByKeyCommand));
                }
            }

        } else if (cmdRepr instanceof RemoveAllByGenreCommandRepresentation) {
            RemoveAllByGenreCommand removeAllByGenreCommand = null;
            try {
                removeAllByGenreCommand
                        = new RemoveAllByGenreCommand(
                        MusicGenre.valueOf(argumentField.getText())
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
                    main.getClient().addRequest(new Request(removeAllByGenreCommand));
                }
            }

        } else if (cmdRepr instanceof MaxByFrontManCommandRepresentation) {
            main.getClient().addRequest(new Request(new MaxByFrontManCommand()));

        } else if (cmdRepr instanceof InsertCommandRepresentation) {
            InsertCommand insertCommand = null;
            try {
                insertCommand
                        = new InsertCommand(
                        Integer.parseInt(argumentField.getText()),
                        main.getEditMusicBandDialogController().getMusicBand()
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
                    main.getClient().addRequest(new Request(insertCommand));
                }
            }
        } else if (cmdRepr instanceof InfoCommandRepresentation) {
            main.getClient().addRequest(new Request(new InfoCommand()));

        } else if (cmdRepr instanceof HelpCommandRepresentation) {
            main.getClient().addRequest(new Request(new HelpCommand()));

        } else if (cmdRepr instanceof FilterGreaterThanSinglesCountCommandRepresentation) {
            FilterGreaterThanSinglesCountCommand filterGreaterThanSinglesCountCommand = null;
            try {
                filterGreaterThanSinglesCountCommand
                        = new FilterGreaterThanSinglesCountCommand(
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
                    main.getClient().addRequest(new Request(filterGreaterThanSinglesCountCommand));
                }
            }

        } else if (cmdRepr instanceof ExecuteScriptCommandRepresentation) {
            ExecuteScriptCommand executeScriptCommand = null;
            try {
                executeScriptCommand
                        = new ExecuteScriptCommand(
                        argumentField.getText()
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
                    main.getClient().addRequest(new Request(executeScriptCommand));
                }
            }
        } else if (cmdRepr instanceof ClearCommandRepresentation) {
            main.getClient().addRequest(new Request(new ClearCommand()));
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
