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
import java.util.Queue;

/**
 * Created by Baran on 5/29/2017.
 */
public class Quarry extends Building {
    public Quarry() {
        this.type = "Quarry";
        this.size = new Pair(4, 4);
    }

    public Quarry(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Quarry";
        this.size = new Pair(4, 4);
    }

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Quarry.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button destroy = new Button("Distroy Building");
        transition(destroy);
        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Quarry.this.mapGUI.removeBuildings(Quarry.this);
            }
        });
        imageView.setLayoutX(60);
        imageView.setLayoutY(20);
        destroy.setLayoutX(250);
        destroy.setLayoutY(60);
        ProgressBar health = new ProgressBar(this.health/100);
        health.setStyle("-fx-accent: #96ff4c;");
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        health.setLayoutY(20);
        health.setPrefSize(100,20);
        anchorPane.getChildren().addAll(imageView, destroy, health);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        if (!owner){
            destroy.setVisible(false);
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

