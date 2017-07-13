package StrongholdCrusader.GameObjects.Ship;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Created by Alireza on 7/13/2017.
 */
public class Ship extends GameObject {
    AnchorPane anchorPane;
    Button destroy;
    public Pair size;
    public int speed;

    public Ship() {
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(5, 4);
        this.type = "Ship";
    }

    public Ship(MapGUI mapGUI) {
        super(mapGUI);
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(5, 4);
        this.type = "Ship";
    }

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializaAnchorPane();

        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        return anchorPane;
    }


    public void initializaAnchorPane() {
        anchorPane = new AnchorPane();
        destroy = new Button("Destroy Ship");
        transition(destroy);
        destroy.setLayoutX(100);
        destroy.setLayoutY(40);
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        anchorPane.getChildren().addAll(destroy);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");

    }

    public void transition(Node button) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(100), button);
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
