package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


/**
 * Created by Baran on 5/29/2017.
 */
public class Farm extends Building {
    public AnchorPane anchorPane;
    private Button destroy;

    public Farm() {
        this.type = "Farm";
        this.size = new Pair(4, 4);
        this.health = Settings.FARM_INITIAL_HEALTH;
    }
    public Farm(MapGUI mapGUI) {
        super(mapGUI);
        setImage(ResourceManager.getImage("Farm"));
        this.type = "Farm";
        this.size = new Pair(4, 4);
        this.health = Settings.FARM_INITIAL_HEALTH;
    }

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializeAnchorPane();
        transition(destroy);

        destroy.setOnAction(event -> Farm.this.mapGUI.removeBuildings(Farm.this));

        if (!owner) {
            destroy.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        ImageView imageView = new ImageView(ResourceManager.getImage("Farm"));
        destroy = new Button("Destroy Building");
        destroy.setGraphic(imageView);
        imageView.setLayoutX(60);
        imageView.setLayoutY(20);
        destroy.setLayoutX(50);
        destroy.setLayoutY(10);
        ProgressBar healthBar = new ProgressBar((double) this.health / Settings.FARM_INITIAL_HEALTH);
        healthBar.setLayoutX(Settings.MENUS_ANCHOR_PANE_WIDTH - 100);
        healthBar.setStyle("-fx-accent : #96ff4c");
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100, 20);
        anchorPane.getChildren().addAll(imageView, destroy, healthBar);
        anchorPane.setId("building");
        anchorPane.setPrefSize(Settings.MENUS_ANCHOR_PANE_WIDTH, Settings.MENUS_ANCHOR_PANE_HEIGHT);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");

    }

    private void transition(Node button) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(100), button);
        transition.setAutoReverse(true);
        button.setOnMouseEntered(event -> {
            transition.setFromX(1);
            transition.setToX(1.1);
            transition.setFromY(1);
            transition.setToY(1.1);
            transition.play();
        });
        button.setOnMouseExited(event -> {
            transition.setFromX(1.1);
            transition.setToX(1);
            transition.setFromY(1.1);
            transition.setToY(1);
            transition.play();
        });
    }
}

