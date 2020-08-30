package ru.itmo.UI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import ru.itmo.UI.Main;

import java.time.LocalDate;
import java.util.Locale;


public class RootPaneController {

    Main main;

    @FXML
    BorderPane pane;

    @FXML
    private Label userLabel;

    @FXML
    private MenuItem russianLanguageMenuItem;

    @FXML
    private MenuItem portugueseLanguageMenuItem;

    @FXML
    private MenuItem spanishLanguageMenuItem;

    @FXML
    private MenuItem italianLanguageMenuItem;


    @FXML
    private void handleRussianLanguageMenuItem() {

        Locale locale = new Locale("ru");

        localizeControllers(locale);
    }


    @FXML
    private void handlePortugueseLanguageMenuItem() {

        Locale locale = new Locale("pt");

        localizeControllers(locale);
    }


    @FXML
    private void handleSpanishLanguageMenuItem() {

        Locale locale = new Locale("es", "PA");

        localizeControllers(locale);
    }


    @FXML
    private void handleItalianLanguageMenuItem() {

        Locale locale = new Locale("it");

        localizeControllers(locale);
    }


    private void localizeControllers(Locale locale) {
        if (main.getCollectionOverviewController() != null) {
            main.getCollectionOverviewController().localize(locale); // TODO: 29.08.2020
        }

        if (main.getCollectionVisualizationController() != null) {
            main.getCollectionVisualizationController().localize(locale); // TODO: 29.08.2020
        }

        if (main.getEditMusicBandDialogController() != null) {
            main.getEditMusicBandDialogController().localize(locale); // TODO: 29.08.2020
        }
    }



    public void setMain(Main main) {
        this.main = main;
    }


    public void setUserLabelText(String userLogin) {
        this.userLabel.setText(userLogin);
    }

}
