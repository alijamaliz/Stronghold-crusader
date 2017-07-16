package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.ResourceManager;
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
        this.health = Settings.PALACE_INITIAL_HEALTH;
    }

    public Palace(MapGUI mapGUI) {
        super(mapGUI);
        setImage(ResourceManager.getImage("Palace"));
        this.type = "Palace";
        this.size = new Pair(6, 6);
        this.health = Settings.PALACE_INITIAL_HEALTH;
    }

    public AnchorPane anchorPane;
    ImageView imageView;
    ImageView vassal;
    ImageView worker;
    Button createVassal;
    ProgressBar healthBar;


    public AnchorPane objectsMenuAnchorPane(boolean owner) {

        initializeAnchorPane();
        transition(createVassal);

        createVassal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Palace.this.position.x - 1;
                int y = Palace.this.position.y - 1;
                Palace.this.mapGUI.createHuman("Vassal", Palace.this.id);
            }
        });


        if (!owner){
            createVassal.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        imageView = new ImageView(ResourceManager.getImage("Palace"));
        vassal = new ImageView(ResourceManager.getImage("Vassal"));
        worker = new ImageView(ResourceManager.getImage("Worker"));
        createVassal = new Button("Create Vassal");
        createVassal.setGraphic(vassal);
        imageView.setLayoutX(40);
        imageView.setLayoutY(-20);
        createVassal.setLayoutX(350);
        createVassal.setLayoutY(60);
        healthBar = new ProgressBar((double)this.health/Settings.PALACE_INITIAL_HEALTH);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100,20);
        anchorPane.getChildren().addAll(imageView, createVassal, healthBar);
        anchorPane.setPrefSize(550, 250);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
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

