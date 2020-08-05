package ru.itmo.UI.view;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.itmo.UI.Main;
import ru.itmo.UI.util.DateUtil;
import ru.itmo.core.common.classes.Color;
import ru.itmo.core.common.classes.Country;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.classes.MusicGenre;


public class CollectionOverviewController {


    private String nullFieldSymbol = "null";
    @FXML
    private TableView<MusicBand> collectionTable;

    @FXML
    private TableColumn<MusicBand, String> idColumn;

    @FXML
    private TableColumn<MusicBand, String> nameColumn;

    @FXML
    private TableColumn<MusicBand, String> coordXColumn;

    @FXML
    private TableColumn<MusicBand, String> coordYColumn;

    @FXML
    private TableColumn<MusicBand, String> creationDateColumn;

    @FXML
    private TableColumn<MusicBand, String> numberOfParticipantsColumn;

    @FXML
    private TableColumn<MusicBand, String> singlesCountColumn;

    @FXML
    private TableColumn<MusicBand, String> musicGenreColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManNameColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManHeightColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManHeirColorColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManNationalityColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManLocXColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManLocYColumn;

    @FXML
    private TableColumn<MusicBand, String> frontManLocNameColumn;

//    @FXML
//    private TableColumn<MusicBand, >

    ///
    private Main main;
    ///


    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );

        nameColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(cellData.getValue().getName())
        );

        coordXColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getX()))
        );

        coordYColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getCoordinates().getY()))
        );

        creationDateColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(DateUtil.dateToStringCanonical(cellData.getValue().getCreationDate()))
        );

        numberOfParticipantsColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getNumberOfParticipants()))
        );

        singlesCountColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getSinglesCount()))
        );

        musicGenreColumn.setCellValueFactory(
                cellData ->
                        new SimpleStringProperty(String.valueOf(cellData.getValue().getGenre()))
        );

        frontManNameColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                        ? new SimpleStringProperty(cellData.getValue().getFrontMan().getName())
                        : new SimpleStringProperty(nullFieldSymbol)
        );

        frontManHeightColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                        ?
                            (cellData.getValue().getFrontMan().getHeight() != null)
                            ? new SimpleStringProperty(String.valueOf(cellData.getValue().getFrontMan().getHeight()))
                            : new SimpleStringProperty(nullFieldSymbol)
                        : new SimpleStringProperty(nullFieldSymbol)
        );

        frontManHeirColorColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                        ?
                            (cellData.getValue().getFrontMan().getHeirColor() != null)
                            ? new SimpleStringProperty(String.valueOf(cellData.getValue().getFrontMan().getHeirColor()))
                            : new SimpleStringProperty(nullFieldSymbol)
                        : new SimpleStringProperty(nullFieldSymbol)
        );

        frontManNationalityColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                        ?
                            (cellData.getValue().getFrontMan().getNationality() != null)
                            ? new SimpleStringProperty(String.valueOf(cellData.getValue().getFrontMan().getNationality()))
                            : new SimpleStringProperty(nullFieldSymbol)
                        : new SimpleStringProperty(nullFieldSymbol)
);

        frontManLocXColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleStringProperty(String.valueOf(cellData.getValue().getFrontMan().getLocation().getX()))
                        : new SimpleStringProperty(nullFieldSymbol)
        );

        frontManLocYColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleStringProperty(String.valueOf(cellData.getValue().getFrontMan().getLocation().getY()))
                        : new SimpleStringProperty(nullFieldSymbol)
        );

        frontManLocNameColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleStringProperty(cellData.getValue().getFrontMan().getLocation().getName())
                        : new SimpleStringProperty(nullFieldSymbol)
        );


    }

    public void setMain(Main main) {
        this.main = main;

        collectionTable.setItems(main.getCollection());
    }
}
