package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    Button createSoldier;
    Button distroy;

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Barracks.png");
        ImageView buildingImage = new ImageView(building.toURI().toString());
        createSoldier = new Button("createSoldier");
        File soldier = new File("Resources/images/Humans/Soldier.png");
        ImageView soldierImage = new ImageView(soldier.toURI().toString());
        distroy = new Button("Distroy Building");
        transition();
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


    public void transition(){
        ScaleTransition distroyTrans = new ScaleTransition(Duration.millis(300),distroy);
        ScaleTransition createSoldierTrans = new ScaleTransition(Duration.millis(300),createSoldier);
        distroyTrans.setFromX(1);
        distroyTrans.setToX(1.1);
        distroyTrans.setFromY(1);
        distroyTrans.setToY(1.1);
        createSoldierTrans.setFromX(1);
        createSoldierTrans.setToX(1.1);
        createSoldierTrans.setFromY(1);
        createSoldierTrans.setToY(1.1);
        createSoldierTrans.setAutoReverse(true);
        distroyTrans.setAutoReverse(true);
        distroy.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                distroyTrans.setFromX(1);
                distroyTrans.setToX(1.1);
                distroyTrans.setFromY(1);
                distroyTrans.setToY(1.1);
                distroyTrans.play();
            }
        });
        distroy.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                distroyTrans.setFromX(1.1);
                distroyTrans.setToX(1);
                distroyTrans.setFromY(1.1);
                distroyTrans.setToY(1);
                distroyTrans.play();
            }
        });
    }
}
