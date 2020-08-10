package ru.itmo.UI.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import ru.itmo.UI.Main;
import ru.itmo.core.common.classes.*;



public class EditMusicBandDialogController {

    //TODO frontManHeight

    private String nullSymbol
            = "null";

    Main main;

    private Stage dialogStage;

    private MusicBand musicBand;
    private boolean submitButtonIsClicked
            = false;

//----------------------------------------------------------------------------------------------------------------------


    @FXML
    private Button submitButton;

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
    private ComboBox<String> frontManComboBox;

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




    @FXML
    private void initialize() {
        //TODO
        initComboBoxes();
    }


    @FXML
    private void handleSubmitButton() throws InterruptedException {

        MusicBand mb;

        try {
            mb = createMusicBand();


            //TODO посмотреть в сову ьфлукн как передавать Зукыт дальше
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.initOwner(dialogStage);
            infoAlert.setContentText("Music bane was created successfully. Watch details in terminal.");
            infoAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//            infoAlert.showAndWait();

            System.out.println(mb); ///reeeeplaacee


            submitButtonIsClicked = true;

            dialogStage.close();
            System.out.println("Stage is closed");

//            Thread.sleep(4000);

//            dialogStage.show(); // delete later


        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setResizable(true);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }


    private MusicBand createMusicBand() {

        StringBuilder errors = new StringBuilder();

        MusicBand mb = new MusicBand();

        mb.setId(1);


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

        if (frontManComboBox.getValue().equals(nullSymbol)) {
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


    @FXML
    private void frontManComboBoxListener(ActionEvent event) {

        if (frontManComboBox.getValue().equals(nullSymbol)) {
            processNullFrontMan();
        } else if (frontManComboBox.getValue().equals("not " + nullSymbol)) {
            processNotNullFrontMan();
        }
    }


    private <T> T considerNull(T value) {
        if (String.valueOf(value).equals(nullSymbol)) return null;
        else return value;
    }


    private boolean isNullSymbol(TextField field) {
        return field.getText().equals(nullSymbol);
    }


    private void initComboBoxes() {

        initFrontManComboBox();
        initMusicGenreComboBox();
        initFrontManHeirColorComboBox();
        initFrontManNationalityComboBox();
    }


    private void initFrontManComboBox() {

        ObservableList<String> frontManComboBoxValues = FXCollections.observableArrayList();
        frontManComboBoxValues.add(nullSymbol);
        frontManComboBoxValues.add("not " + nullSymbol);

        frontManComboBox.setItems(frontManComboBoxValues);

        frontManComboBox.getSelectionModel().select(1);
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


    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
