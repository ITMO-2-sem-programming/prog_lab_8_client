package ru.itmo.UI.controller;

import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.itmo.UI.Main;
import ru.itmo.core.common.exchange.request.clientCommand.userCommand.UpdateCommand;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.util.CirclePositioner;
import ru.itmo.util.mapper.Mapper;
import ru.itmo.util.mapper.MappingRange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CollectionVisualizationController {

    private Main main;

    private Stage stage;

    @FXML
    private AnchorPane pane;



//----------------------------------------------------------------------------------------------------------------------


    private Integer chosenCircleID;
    private Color ownedElementsFillColor
            = Color.TEAL;
    private Color ownedElementsStrokeColor
            = Color.DEEPPINK;
    private double initialOpacity
            = 0;
    private double ownedElementsStrokeWidth
            = 4;
    private double animationDurationMillis
            = 3000;
    private double opacityStartValue
            = 0;
    private double opacityEndValue
            = 10;
    private int animationCycleCount
            = 1;
    private double animationDelayLowerBoundMillis
            = 600;
    private double animationDelayHigherBoundMillis
            = 3000;
    private List<FadeTransition> animationList
            = new ArrayList<>();
    private EventHandler<ContextMenuEvent> contextMenuHandler;


    @FXML
    private void initialize() {

        stage = new Stage();
        stage.setTitle("Visualize collection");
        stage.setResizable(false);
        stage.initModality(Modality.NONE);
//        stage.setWidth(new CirclePositioner(120, 20, 6).getRowWidth()); // todo Non-tested !!!

        Scene scene = new Scene(pane);
        stage.setScene(scene);


        stage.setOnShowing(
                event -> {
                    System.out.println("setOnShowing()");

                    visualize(true);
                }
        );


        ContextMenu contextMenu = new ContextMenu();

        MenuItem editMenuItem = new MenuItem("Edit");


        editMenuItem.setOnAction(
                event -> {
                    EditMusicBandDialogController editMusicBandDialogController = main.loadEditMusicBandDialog();
                    MusicBand editingMusicBand = main.getCollection().stream().filter(
                            (element) -> element.getId().equals(chosenCircleID)).findAny().orElse(null);
                    editMusicBandDialogController.setMusicBand(editingMusicBand);

                    Stage stage = editMusicBandDialogController.getStage();
                    stage.showAndWait();

                    MusicBand newMusicBand = editMusicBandDialogController.getMusicBand();


                    if (newMusicBand != null) {
                        main.getClient().addRequest(new Request(
                                new UpdateCommand(newMusicBand.getId(), newMusicBand)));
                    }

                });


        contextMenu.getItems().add(editMenuItem);

        // When user right-click on Circle
        contextMenuHandler =
                event -> {

                    chosenCircleID = Integer.parseInt(event.getPickResult().getIntersectedNode().getId());
                    editMenuItem.setDisable(
                            ! main.getOwnedElementsID().contains(chosenCircleID)
                    );

                    System.out.println("Node ID : " + event.getPickResult().getIntersectedNode().getId());
                    contextMenu.show(event.getPickResult().getIntersectedNode(), event.getScreenX(), event.getScreenY());
                };

    }


    private void initializeAfterMainSet() {

        main.getCollection().addListener(
                (ListChangeListener<MusicBand>) c -> {
                    visualize(false);
                    System.out.println("CollectionVisualizationController :_ The list has been changed.");
                }
        );
    }


    private void visualize(boolean animationEnabled) {

        CirclePositioner positioner = new CirclePositioner(120, 20, 6);

        System.out.println("ObservList size : " + main.getCollection().size());
        Mapper mapper = new Mapper(
                new MappingRange(1,10),
                new MappingRange(60, 120)
        );

        System.out.println("Trying to be visualized...");
        // Clean the pane from old circles
        pane.getChildren().clear();


        animationList = new ArrayList<>();
        if ( animationEnabled ) {

            for (int i = 0; i < main.getCollection().size(); i++) {

                FadeTransition fade = new FadeTransition();

                //setting the duration for the Fade transition
                fade.setDuration(Duration.millis(animationDurationMillis));

                //setting the initial and the target opacity value for the transition
                fade.setFromValue(opacityStartValue);
                fade.setToValue(opacityEndValue);
                fade.setCycleCount(animationCycleCount);
                fade.setDelay(Duration.millis(getRandomAnimationDelayMillis()));

                animationList.add(fade);
            }
        }

        Iterator<FadeTransition> animationListIterator = animationList.iterator();

        for (MusicBand mb : main.getCollection()) {
            Circle circle = new Circle();
            circle.setId(String.valueOf(mb.getId()));
            if (main.getOwnedElementsID().contains(mb.getId())) {
                circle.setFill(ownedElementsFillColor);
                circle.setStroke(ownedElementsStrokeColor);
                circle.setStrokeWidth(ownedElementsStrokeWidth);
            } else circle.setFill(RandomColor.randomColor());

            circle.setRadius(mapper.map(mb.getSinglesCount()));
            positioner.position(circle);
            Tooltip.install(circle,
                    new Tooltip(mb.toString()));
            circle.setOnContextMenuRequested(contextMenuHandler);
            pane.getChildren().add(circle);

            if (animationEnabled) {
                circle.setOpacity(0);
                if (animationListIterator.hasNext()) {
                    animationListIterator.next().setNode(circle);
                }
            }
        }

//        Circle circleDeleteLater = new Circle();
//        circleDeleteLater.setFill(ownedElementsStrokeColor);
//        circleDeleteLater.setCenterX(40);
//        circleDeleteLater.setCenterY(50);
//        circleDeleteLater.setRadius(30);
//        pane.getChildren().add(circleDeleteLater);



        if ( animationEnabled ) {
            playAnimation();
        }


//        stage.getScene().setRoot(pane);
//        stage.show();
//        BorderPane examplePane = new BorderPane();
//        examplePane.setCenter(circleDeleteLater);
//        stage.setScene(new Scene(examplePane));

    }


    private void playAnimation() {
        for ( FadeTransition fade : animationList ) {
            fade.play();
        }
    }


    private double getRandomAnimationDelayMillis() {
        return
                animationDelayLowerBoundMillis
                        +
                        Math.random()
                                * ( animationDelayHigherBoundMillis - animationDelayLowerBoundMillis );
    }



//----------------------------------------------------------------------------------------------------------------------


    // Getters and setters section


    public void setMain(Main main) {
        this.main = main;

        initializeAfterMainSet();

    }

    public Main getMain() {
        return main;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public Integer getChosenCircleID() {
        return chosenCircleID;
    }

    public void setChosenCircleID(Integer chosenCircleID) {
        this.chosenCircleID = chosenCircleID;
    }

    public Color getOwnedElementsFillColor() {
        return ownedElementsFillColor;
    }

    public void setOwnedElementsFillColor(Color ownedElementsFillColor) {
        this.ownedElementsFillColor = ownedElementsFillColor;
    }

    public Color getOwnedElementsStrokeColor() {
        return ownedElementsStrokeColor;
    }

    public void setOwnedElementsStrokeColor(Color ownedElementsStrokeColor) {
        this.ownedElementsStrokeColor = ownedElementsStrokeColor;
    }

    public double getInitialOpacity() {
        return initialOpacity;
    }

    public void setInitialOpacity(double initialOpacity) {
        this.initialOpacity = initialOpacity;
    }

    public double getOwnedElementsStrokeWidth() {
        return ownedElementsStrokeWidth;
    }

    public void setOwnedElementsStrokeWidth(double ownedElementsStrokeWidth) {
        this.ownedElementsStrokeWidth = ownedElementsStrokeWidth;
    }

    public double getAnimationDurationMillis() {
        return animationDurationMillis;
    }

    public void setAnimationDurationMillis(double animationDurationMillis) {
        this.animationDurationMillis = animationDurationMillis;
    }

    public double getOpacityStartValue() {
        return opacityStartValue;
    }

    public void setOpacityStartValue(double opacityStartValue) {
        this.opacityStartValue = opacityStartValue;
    }

    public double getOpacityEndValue() {
        return opacityEndValue;
    }

    public void setOpacityEndValue(double opacityEndValue) {
        this.opacityEndValue = opacityEndValue;
    }

    public int getAnimationCycleCount() {
        return animationCycleCount;
    }

    public void setAnimationCycleCount(int animationCycleCount) {
        this.animationCycleCount = animationCycleCount;
    }

    public double getAnimationDelayLowerBoundMillis() {
        return animationDelayLowerBoundMillis;
    }

    public void setAnimationDelayLowerBoundMillis(double animationDelayLowerBoundMillis) {
        this.animationDelayLowerBoundMillis = animationDelayLowerBoundMillis;
    }

    public double getAnimationDelayHigherBoundMillis() {
        return animationDelayHigherBoundMillis;
    }

    public void setAnimationDelayHigherBoundMillis(double animationDelayHigherBoundMillis) {
        this.animationDelayHigherBoundMillis = animationDelayHigherBoundMillis;
    }

}



class RandomColor {

    public static Color randomColor() {
        return Color.color(Math.random(), Math.random(), Math.random());

    }

}
