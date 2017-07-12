package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Palace extends Building {
    public Palace() {
        this.type = "Palace";
        this.size = new Pair(6, 6);
    }

    public Palace(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Palace";
        this.size = new Pair(6, 6);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        ImageView imageView = new ImageView(mapGUI.getResourceManager().getImage("Palace"));
        ImageView vassal = new ImageView(mapGUI.getResourceManager().getImage("Vassal"));
        ImageView worker = new ImageView(mapGUI.getResourceManager().getImage("Worker"));
        Button createVassal = new Button("Create Vassal");
        Button createWorker = new Button("Create Worker");
        createVassal.setGraphic(vassal);
        createWorker.setGraphic(worker);
        transition(createVassal);
        transition(createWorker);
        createVassal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Palace.this.position.x - 1;
                int y = Palace.this.position.y - 1;
                Palace.this.mapGUI.createHuman("Vassal", Palace.this.id);
            }
        });
        createWorker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Palace.this.position.x + Palace.this.size.x;
                int y = Palace.this.position.y + Palace.this.size.y;
                Palace.this.mapGUI.createHuman("Worker", Palace.this.id);
            }
        });
        imageView.setLayoutX(40);
        imageView.setLayoutY(-20);
        createVassal.setLayoutX(350);
        createWorker.setLayoutX(550);
        createVassal.setLayoutY(60);
        createWorker.setLayoutY(60);
        ProgressBar health = new ProgressBar(this.health/100);
        health.setStyle("-fx-accent: #96ff4c;");
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        health.setLayoutY(20);
        health.setPrefSize(100,20);
        anchorPane.getChildren().addAll(imageView, createVassal, createWorker,health);
        anchorPane.setPrefSize(550, 250);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        if (!owner){
            createVassal.setVisible(false);
            createWorker.setVisible(false);
        }
        return anchorPane;
    }

    public void transition(Node button){
        ScaleTransition transition = new ScaleTransition(Duration.millis(100),button);
        transition.setAutoReverse(true);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1);
                transition.setToX(1.1);
                transition.setFromY(1);
                transition.setToY(1.1);
                transition.play();
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1.1);
                transition.setToX(1);
                transition.setFromY(1.1);
                transition.setToY(1);
                transition.play();
            }
        });
    }
}

