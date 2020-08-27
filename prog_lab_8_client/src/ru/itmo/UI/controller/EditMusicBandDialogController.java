package ru.itmo.UI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.itmo.UI.Main;
import ru.itmo.core.common.classes.*;



public class EditMusicBandDialogController {


    private String nullSymbol
            = "null";

    private Main main;

    private Stage stage;

    @FXML
    private BorderPane pane;

    private MusicBand musicBand
            = null;
//    private boolean submitButtonIsClicked
//            = false;

//----------------------------------------------------------------------------------------------------------------------


//    @FXML
//    private Button submitButton;
//
//    @FXML
//    private Button cancelButton;

//----------------------------------------------------------------------------------------------------------------------


    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField coordXField;

    @FXML
    private TextField coordYField;

    @FXML
    private TextField creationDateField;

    @FXML
    private TextField numberOfParticipantsField;

    @FXML
    private TextField singlesCountField;

    @FXML
    private ComboBox<MusicGenre> musicGenreComboBox;

    @FXML
    private ComboBox<FrontManComboBoxOptions> frontManComboBox;

    @FXML
    private TextField frontManNameField;

    @FXML
    private TextField frontManHeightField;

    @FXML
    private ComboBox<Color> frontManHeirColorComboBox;

    @FXML
    private ComboBox<Country> frontManNationalityComboBox;

    @FXML
    private TextField frontManLocXField;

    @FXML
    private TextField frontManLocYField;

    @FXML
    private TextField frontManLocNameField;


//----------------------------------------------------------------------------------------------------------------------


    // Methods


    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setTitle("Edit Music band");
        stage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        
        stage.setOnCloseRequest( //// TODO: 8/18/20   Pay pay attention here 
                event -> 
                        musicBand = null
        );

        initElements();
//        setFields();

//        System.out.println("initialize()   method execs");
    }


    private void initElements() {
        initComboBoxes();

        nameField.requestFocus();
    }


    private void initComboBoxes() {

        initFrontManComboBox();
        initMusicGenreComboBox();
        initFrontManHeirColorComboBox();
        initFrontManNationalityComboBox();

    }


    private void initFrontManComboBox() {

        ObservableList<FrontManComboBoxOptions> frontManComboBoxValues = FXCollections.observableArrayList();
        frontManComboBoxValues.add(FrontManComboBoxOptions.NOT_NULL);
        frontManComboBoxValues.add(FrontManComboBoxOptions.NULL);


        frontManComboBox.setItems(frontManComboBoxValues);

        frontManComboBox.getSelectionModel().select(FrontManComboBoxOptions.NOT_NULL);
    }


    private void initMusicGenreComboBox() {
        musicGenreComboBox.getItems().addAll(MusicGenre.values());
    }


    private void initFrontManHeirColorComboBox() {
        frontManHeirColorComboBox.getItems().addAll(Color.values());
    }


    private void initFrontManNationalityComboBox() {
        frontManNationalityComboBox.getItems().addAll(Country.values());
    }


    @FXML
    private void handleSubmitButton() {

        try {

            musicBand = createMusicBand();


            //TODO посмотреть в сову ьфлукн как передавать Зукыт дальше
//            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
//            infoAlert.initOwner(dialogStage);
//            infoAlert.setContentText("Music bane was created successfully. Watch details in terminal.");
//            infoAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//            infoAlert.showAndWait();


//            System.out.println(mb); ///reeeeplaacee


//            submitButtonIsClicked = true;

            stage.close();
//            System.out.println("Stage is closed");

//            Thread.sleep(4000);

//            dialogStage.show(); // delete later

        } catch (IllegalArgumentException e) {

            musicBand = null;

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.initOwner(stage);

            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }
    }


    @FXML
    private void handleCancelButton() {
        musicBand = null;
        stage.close();
    }


    @FXML
    private void handleFrontManComboBox() {

        if (frontManComboBox.getValue().equals(FrontManComboBoxOptions.NULL)) {
            processNullFrontMan();
        } else if (frontManComboBox.getValue().equals(FrontManComboBoxOptions.NOT_NULL)) {
            processNotNullFrontMan();
        }
    }


    private MusicBand createMusicBand() {

        StringBuilder errors = new StringBuilder();

        MusicBand mb = new MusicBand();

        if (musicBand != null) {
            mb.setId(musicBand.getId());
        } else {
            mb.setId(1);

        }

        try {
            mb.setName(nameField.getText());
        } catch (IllegalArgumentException e) {
            errors.append(MusicBand.musicBandFieldsDescription.get("name")).append("\n\n");
        }


        Coordinates coordinates = new Coordinates();

        try {
            coordinates.setX(Double.parseDouble(coordXField.getText()));
        } catch (IllegalArgumentException e) {
            errors.append(Coordinates.coordinatesFieldsDescription.get("x")).append("\n\n");
        }

        try {
            coordinates.setY(Integer.parseInt(coordYField.getText()));
        } catch (IllegalArgumentException e) {
            errors.append(Coordinates.coordinatesFieldsDescription.get("y")).append("\n\n");
        }

        mb.setCoordinates(coordinates);


        try {
            mb.setNumberOfParticipants(Long.parseLong(numberOfParticipantsField.getText()));
        } catch (IllegalArgumentException e) {
            errors.append(MusicBand.musicBandFieldsDescription.get("numberOfParticipants")).append("\n\n");
        }


        try {
            mb.setSinglesCount(Integer.parseInt(singlesCountField.getText()));
        } catch (IllegalArgumentException e) {
            errors.append(MusicBand.musicBandFieldsDescription.get("singlesCount")).append("\n\n");
        }


        try {
            mb.setGenre(musicGenreComboBox.getValue());
        } catch (IllegalArgumentException e) {
            errors.append(e.getMessage()).append("\n\n");
        }


        Person person = new Person();

        if (frontManComboBox.getValue().equals(FrontManComboBoxOptions.NULL)) {
            person = null;
            processNullFrontMan();

        } else {

            try {
                person.setName(frontManNameField.getText());
            } catch (IllegalArgumentException e) {
                errors.append(Person.personFieldsDescription.get("name")).append("\n\n");
            }

            try {
                if (isNullSymbol(frontManHeightField)) {
                    person.setHeight(null);
                } else {
                    person.setHeight(Long.parseLong(frontManHeightField.getText()));
                }
            } catch (IllegalArgumentException e) {
                errors.append(Person.personFieldsDescription.get("height")).append("\n\n");
            }

            try {
                person.setHeirColor(frontManHeirColorComboBox.getValue());
            } catch (IllegalArgumentException e) {
                errors.append(e.getMessage()).append("\n\n");
            }

            try {
                person.setNationality(frontManNationalityComboBox.getValue());
            } catch (IllegalArgumentException e) {
                errors.append(e.getMessage()).append("\n\n");
            }

            Location location = new Location();

            try {
                location.setX(Integer.parseInt(frontManLocXField.getText()));
            } catch (IllegalArgumentException e) {
                errors.append(Location.locationFieldsDescription.get("x")).append("\n\n");
            }

            try {
                location.setY(Integer.parseInt(frontManLocYField.getText()));
            } catch (IllegalArgumentException e) {
                errors.append(Location.locationFieldsDescription.get("y")).append("\n\n");
            }

            try {
                location.setName(frontManLocNameField.getText());
            } catch (IllegalArgumentException e) {
                errors.append(Location.locationFieldsDescription.get("name")).append("\n\n");
            }


            person.setLocation(location);

        }

        mb.setFrontMan(person);



        if (errors.length() != 0) {

            mb = null;

            throw new IllegalArgumentException(errors.toString());
        }

        return mb;
    }


    private void processNullFrontMan() {

        frontManNameField.setDisable(true);
        frontManHeightField.setDisable(true);
        frontManHeirColorComboBox.setDisable(true);
        frontManNationalityComboBox.setDisable(true);
        frontManLocXField.setDisable(true);
        frontManLocYField.setDisable(true);
        frontManLocNameField.setDisable(true);

    }


    private void processNotNullFrontMan() {

        frontManNameField.setDisable(false);
        frontManHeightField.setDisable(false);
        frontManHeirColorComboBox.setDisable(false);
        frontManNationalityComboBox.setDisable(false);
        frontManLocXField.setDisable(false);
        frontManLocYField.setDisable(false);
        frontManLocNameField.setDisable(false);

    }


    private void setFields() {

        if (this.musicBand == null) {
            clearAllFields();

        } else {
            idField.setText(musicBand.getId().toString());
            nameField.setText(musicBand.getName());
            coordXField.setText(String.valueOf(musicBand.getCoordinates().getX()));
            coordYField.setText(String.valueOf(musicBand.getCoordinates().getY()));
            creationDateField.setText(musicBand.getCreationDate().toString());
            numberOfParticipantsField.setText(String.valueOf(musicBand.getNumberOfParticipants()));
            singlesCountField.setText(String.valueOf(musicBand.getSinglesCount()));
            musicGenreComboBox.setValue(musicBand.getGenre());

            if (musicBand.getFrontMan() == null) {

                frontManComboBox.getSelectionModel().select(FrontManComboBoxOptions.NULL);
                processNullFrontMan();

            } else {

                frontManComboBox.getSelectionModel().select(FrontManComboBoxOptions.NOT_NULL);
                frontManNameField.setText(processNullToString(musicBand.getFrontMan().getName()));
                frontManHeightField.setText(processNullToString(musicBand.getFrontMan().getHeight()));
                frontManHeirColorComboBox.setValue(musicBand.getFrontMan().getHeirColor());
                frontManNationalityComboBox.setValue(musicBand.getFrontMan().getNationality());
                frontManLocXField.setText(processNullToString(musicBand.getFrontMan().getLocation().getX()));
                frontManLocYField.setText(processNullToString(musicBand.getFrontMan().getLocation().getY()));
                frontManLocNameField.setText(processNullToString(musicBand.getFrontMan().getLocation().getName()));
            }

        }

    }


    private void clearAllFields() {

//        idField.clear();
        nameField.clear();
        coordXField.clear();
        coordYField.clear();
//        creationDateField.clear();
        numberOfParticipantsField.clear();
        singlesCountField.clear();
        musicGenreComboBox.getSelectionModel().clearSelection();
        frontManComboBox.getSelectionModel().selectFirst();
        frontManNameField.clear();
        frontManHeightField.clear();
        frontManHeirColorComboBox.getSelectionModel().clearSelection();
        frontManNationalityComboBox.getSelectionModel().clearSelection();
        frontManLocXField.clear();
        frontManLocYField.clear();
        frontManLocNameField.clear();


    }


    private boolean isNullSymbol(TextField field) {
        return field.getText().equals(nullSymbol);
    }


    private String processNullToString(Object object) {
        if (object == null) return nullSymbol;
        else return object.toString();
    }



//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setMain(Main main) {
        this.main = main;
    }


    public MusicBand getMusicBand() {
        return musicBand;
    }


    public void setMusicBand(MusicBand mb) {
        this.musicBand = mb;
        setFields();
    }


    public String getNullSymbol() {
        return nullSymbol;
    }

    public void setNullSymbol(String nullSymbol) {
        this.nullSymbol = nullSymbol;
    }



}




//----------------------------------------------------------------------------------------------------------------------


// Inner classes


enum FrontManComboBoxOptions {
    NULL,
    NOT_NULL
}
