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
public class Port extends Building {
    public Port() {
        this.type = "Port";
        this.size = new Pair(4, 4);
    }

    public Port(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Port";
        this.size = new Pair(4, 4);
    }

    public AnchorPane anchorPane;
    Button destroy;
    ImageView imageView;
    ProgressBar healthBar;

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        imageView = new ImageView(mapGUI.getResourceManager().getImage("Port"));
        destroy = new Button("Destroy Building");
        destroy.setGraphic(imageView);
        transition(destroy);
        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Port.this.mapGUI.removeBuildings(Port.this);
            }
        });
        destroy.setLayoutX(50);
        destroy.setLayoutY(10);
        healthBar = new ProgressBar((double)this.health/100);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100,20);
        anchorPane.getChildren().addAll(destroy,healthBar);
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

