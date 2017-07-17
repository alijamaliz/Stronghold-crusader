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

public class Barracks extends Building {
    public AnchorPane anchorPane;
    private Button createSoldier;
    private Button destroy;
    public Barracks() {
        this.type = "Barracks";
        this.size = new Pair(5, 5);
        this.health = Settings.BARRACKS_INITIAL_HEALTH;
    }
    public Barracks(MapGUI mapGUI) {
        super(mapGUI);
        setImage(ResourceManager.getImage("Barracks"));
        this.type = "Barracks";
        this.size = new Pair(5, 5);
        this.health = Settings.BARRACKS_INITIAL_HEALTH;
    }

    @Override
    public AnchorPane objectsMenuAnchorPane(boolean owner) {

        initializeAnchorPane();
        transition(createSoldier);
        transition(destroy);

        destroy.setOnAction(event -> Barracks.this.mapGUI.removeBuildings(Barracks.this));
        createSoldier.setOnAction(event -> Barracks.this.mapGUI.createHuman("Soldier", Barracks.this.id));

        if (!owner) {
            destroy.setVisible(false);
            createSoldier.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        ImageView imageView = new ImageView(ResourceManager.getImage("Barracks"));
        ImageView soldierImage = new ImageView(ResourceManager.getImage("Soldier"));
        imageView.setScaleX(0.8);
        imageView.setScaleY(0.8);
        createSoldier = new Button("createSoldier");
        destroy = new Button("Destroy Building");
        destroy.setGraphic(imageView);
        createSoldier.setGraphic(soldierImage);
        imageView.setLayoutX(20);
        imageView.setLayoutY(imageView.getLayoutY());
        destroy.setLayoutX(100);
        destroy.setLayoutY(10);
        soldierImage.setLayoutX(400);
        soldierImage.setLayoutY(10);
        createSoldier.setLayoutX(500);
        createSoldier.setLayoutY(60);
        ProgressBar healthBar = new ProgressBar((double) this.health);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHOR_PANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100, 20);
        anchorPane.getChildren().addAll(imageView, createSoldier, soldierImage, destroy, healthBar);
        anchorPane.setPrefSize(Settings.MENUS_ANCHOR_PANE_WIDTH, Settings.MENUS_ANCHOR_PANE_HEIGHT);
        anchorPane.setId("building");
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
