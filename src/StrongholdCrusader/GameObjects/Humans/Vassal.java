package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Vassal extends Human {
    public AnchorPane anchorPane;

    public Vassal() {
        this.type = "Vassal";
        this.speed = Settings.VASSAL_SPEED;
        this.zone = Settings.VASSAL_ATTACK_RADIUS;
        this.power = Settings.HUMAN_POWER;
        this.health = 500;
    }

    public Vassal(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Vassal";
        this.speed = Settings.VASSAL_SPEED;
        this.zone = Settings.VASSAL_ATTACK_RADIUS;
        this.power = Settings.HUMAN_POWER;
        this.health = 500;
    }

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Vassal.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        CheckBox checkBox = new CheckBox("Can Climb ?");
        checkBox.setLayoutX(400);
        checkBox.setLayoutY(60);
        checkBox.setSelected(canClimb);
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mapGUI.changeClimbStatus(Vassal.this, checkBox.isSelected());
            }
        });
        imageView.setLayoutX(200);
        ProgressBar health = new ProgressBar(this.health / 100);
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 150);
        health.setStyle("-fx-accent: #96ff4c;");
        health.setLayoutY(20);
        health.setPrefSize(100, 20);
        anchorPane.getChildren().addAll(imageView, checkBox, health);
        imageView.setLayoutX(200);
        imageView.setLayoutY(40);
        anchorPane.setPrefSize(300, 100);
        return anchorPane;
    }
}
