package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Barracks extends Building {
    public Barracks() {
        this.type = "Barracks";
        this.size = new Pair(5, 5);
    }

    public Barracks(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Barracks";
        this.size = new Pair(5, 5);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Barracks.png");
        ImageView buildingImage = new ImageView(building.toURI().toString());
        Button createSoldier = new Button("createSoldier");
        File soldier = new File("Resources/images/Humans/Soldier.png");
        ImageView soldierImage = new ImageView(soldier.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Barracks.this.mapGUI.removeBuildings(Barracks.this);
            }
        });
        createSoldier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Barracks.this.position.x + Barracks.this.size.x;
                int y = Barracks.this.position.y + Barracks.this.size.y;
                Barracks.this.mapGUI.createHuman("Soldier", new Pair(x, y));
            }
        });
        buildingImage.setLayoutX(20);
        buildingImage.setLayoutY(buildingImage.getLayoutY());
        distroy.setLayoutX(210);
        distroy.setLayoutY(60);
        soldierImage.setLayoutX(400);
        soldierImage.setLayoutY(40);
        createSoldier.setLayoutX(480);
        createSoldier.setLayoutY(60);
        ProgressBar health = new ProgressBar(this.health/100);
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        health.setLayoutY(20);
        health.setPrefSize(100,20);
        anchorPane.getChildren().addAll(buildingImage, createSoldier, soldierImage, distroy,health);
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        if (!owner){
            distroy.setVisible(false);
            createSoldier.setVisible(false);
        }
        return anchorPane;
    }
}
