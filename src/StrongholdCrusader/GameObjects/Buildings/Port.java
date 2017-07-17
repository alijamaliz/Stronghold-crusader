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
public class Port extends Building {

    public AnchorPane anchorPane;
    private Button destroy;
    private Button createShip;

    public Port() {
        this.type = "Port";
        this.size = new Pair(4, 4);
        this.health = Settings.PORT_INITIAL_HEALTH;
    }
    public Port(MapGUI mapGUI) {
        super(mapGUI);
        setImage(ResourceManager.getImage("Port"));
        this.type = "Port";
        this.size = new Pair(4, 4);
        this.health = Settings.PORT_INITIAL_HEALTH;
    }

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializeAnchorPane();
        transition(destroy);
        transition(createShip);
        destroy.setOnAction(event -> Port.this.mapGUI.removeBuildings(Port.this));

        createShip.setOnMouseClicked(event -> {
            MapGUI.gameMode = MapGUI.GameMode.CREATING_SHIP;
            mapGUI.changeCursor("Ship");
        });

        if (!owner) {
            destroy.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        ImageView imageView = new ImageView(ResourceManager.getImage("Port"));
        destroy = new Button("Destroy Building");
        createShip = new Button("Create Ship");
        createShip.setStyle("-fx-font-size: 20px;-fx-text-fill: #411e5e");
        destroy.setGraphic(imageView);
        destroy.setLayoutX(50);
        destroy.setLayoutY(10);
        ProgressBar healthBar = new ProgressBar((double) this.health / Settings.PORT_INITIAL_HEALTH);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHOR_PANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100, 20);
        createShip.setLayoutX(500);
        createShip.setLayoutY(60);
        anchorPane.getChildren().addAll(destroy, healthBar, createShip);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHOR_PANE_WIDTH, Settings.MENUS_ANCHOR_PANE_HEIGHT);
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

