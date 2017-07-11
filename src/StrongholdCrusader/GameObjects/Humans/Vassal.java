package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Map.MapTile;
import StrongholdCrusader.MenuGUI;
import StrongholdCrusader.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Vassal extends Human {
    public Vassal()
    {
        this.type="Vassal";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public Vassal(MapGUI mapGUI) {
        super(mapGUI);
        this.type="Vassal";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Vassal.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        CheckBox checkBox = new CheckBox("Can Climb ?");
        checkBox.setLayoutX(400);
        checkBox.setLayoutY(60);
        boolean canClimb = false;
        canClimb = checkBox.isSelected();
        checkBox.setSelected(canClimb);
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    mapGUI.changeClimbStatus(Vassal.this,checkBox.isSelected());
            }
        });
        imageView.setLayoutX(200);
        ProgressBar health = new ProgressBar(this.health/100);
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        health.setLayoutY(20);
        health.setPrefSize(100,20);
        anchorPane.getChildren().addAll(imageView,checkBox,health);
        imageView.setLayoutX(200);
        imageView.setLayoutY(40);
        anchorPane.setPrefSize(300,100);
        return anchorPane;
    }
}
