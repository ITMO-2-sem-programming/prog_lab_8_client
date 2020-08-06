package ru.itmo.UI.controller;

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

import java.util.Date;


public class CollectionOverviewController {


    private String nullFieldSymbol = "null";
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

//    @FXML
//    private TableColumn<MusicBand, >

    ///
    private Main main;
    ///


    @FXML
    private void initialize() {

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
                            : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManNameColumn.setComparator(
                (name, otherName) -> {
                    if (name.equals(nullFieldSymbol)) return -1;
                    else if (otherName.equals(nullFieldSymbol)) return 1;
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
                                : new SimpleObjectProperty<>(nullFieldSymbol)
                            : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManHeightColumn.setComparator(
                (height, otherHeight) -> {
                    if (height.equals(nullFieldSymbol)) return -1;
                    else if (otherHeight.equals(nullFieldSymbol)) return 1;
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
                                : new SimpleObjectProperty<>(nullFieldSymbol)
                            : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManHeirColorColumn.setComparator(
                (color, otherColor) -> {
                    if (color.equals(nullFieldSymbol)) return -1;
                    else if (otherColor.equals(nullFieldSymbol)) return 1;
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
                                    : new SimpleObjectProperty<>(nullFieldSymbol)
                            : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManNationalityColumn.setComparator(
                (country, otherCountry) -> {
                    if (country.equals(nullFieldSymbol)) return -1;
                    else if (otherCountry.equals(nullFieldSymbol)) return 1;
                    else return ((Country) country).compareTo((Country) otherCountry);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocXColumn.setCellValueFactory(
                cellData ->
                        (cellData.getValue().getFrontMan() != null)
                            ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getX())
                            : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManLocXColumn.setComparator(
                (locX, otherLocX) -> {
                    if (locX.equals(nullFieldSymbol)) return -1;
                    else if (otherLocX.equals(nullFieldSymbol)) return 1;
                    else return ((Integer) locX).compareTo((Integer) otherLocX);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocYColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getY())
                        : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManLocYColumn.setComparator(
                (locY, otherLocY) -> {
                    if (locY.equals(nullFieldSymbol)) return -1;
                    else if (otherLocY.equals(nullFieldSymbol)) return 1;
                    else return ((Integer) locY).compareTo((Integer) otherLocY);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


        frontManLocNameColumn.setCellValueFactory(
                cellData -> (cellData.getValue().getFrontMan() != null)
                        ? new SimpleObjectProperty<>(cellData.getValue().getFrontMan().getLocation().getName())
                        : new SimpleObjectProperty<>(nullFieldSymbol)
        );

        frontManLocNameColumn.setComparator(
                (name, otherName) -> {
                    if (name.equals(nullFieldSymbol)) return -1;
                    else if (otherName.equals(nullFieldSymbol)) return 1;
                    else return ((String) name).compareTo((String) otherName);
                }
        );

//----------------------------------------------------------------------------------------------------------------------


    }


    private int comparatorBooleanToInt(boolean condition) {

        if (condition) return 1;
        else return -1;

    }

    public void setMain(Main main) {
        this.main = main;

        collectionTable.setItems(main.getCollection());
    }


    public String getNullFieldSymbol() {
        return nullFieldSymbol;
    }


    public void setNullFieldSymbol(String nullFieldSymbol) {
        this.nullFieldSymbol = nullFieldSymbol;
    }


}
