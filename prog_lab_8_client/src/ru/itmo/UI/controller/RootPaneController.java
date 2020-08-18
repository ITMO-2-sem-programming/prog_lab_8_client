package ru.itmo.UI.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import ru.itmo.UI.Main;

public class RootPaneController {

    Main main;

    @FXML
    BorderPane pane;





    public void setMain(Main main) {
        this.main = main;
    }
}
