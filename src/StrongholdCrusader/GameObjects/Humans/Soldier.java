package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Soldier extends Human {

    public AnchorPane anchorPane;

    public Soldier() {
        this.type = "Soldier";
        this.speed = Settings.SOLDIER_SPEED;
        this.zone = Settings.SOLDIER_ATTACK_RADIUS;
        this.power = Settings.SOLDIER_POWER;
        this.health = 1000;
    }

    public Soldier(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Soldier";
        this.speed = Settings.SOLDIER_SPEED;
        this.power = Settings.SOLDIER_POWER;
        this.zone = Settings.SOLDIER_ATTACK_RADIUS;
        this.health = 1000;
    }

    @Override
    public AnchorPane clickAction(boolean owner) {

        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Soldier.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        Button button = new Button("Change to Vassal");
        CheckBox checkBox = new CheckBox("Can Climb ?");
        checkBox.setLayoutX(400);
        checkBox.setLayoutY(60);
        checkBox.setSelected(canClimb);
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mapGUI.changeClimbStatus(Soldier.this, checkBox.isSelected());
            }
        });
        imageView.setLayoutX(100);
        imageView.setLayoutY(40);
        button.setLayoutX(200);
        button.setLayoutY(50);
        ProgressBar health = new ProgressBar(this.health / 100);
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 150);
        health.setStyle("-fx-accent: #96ff4c;");
        health.setLayoutY(20);
        health.setPrefSize(100, 20);
        anchorPane.getChildren().addAll(imageView, button, checkBox, health);
        anchorPane.setPrefHeight(100);
        anchorPane.setPrefWidth(300);
        return anchorPane;
    }
}
