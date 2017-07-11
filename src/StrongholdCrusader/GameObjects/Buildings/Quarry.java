package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.io.File;
import java.util.Queue;

/**
 * Created by Baran on 5/29/2017.
 */
public class Quarry extends Building {
    public Quarry() {
        this.type = "Quarry";
        this.size = new Pair(4, 4);
    }

    public Quarry(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Quarry";
        this.size = new Pair(4, 4);
    }

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Quarry.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Quarry.this.mapGUI.removeBuildings(Quarry.this);
            }
        });
        imageView.setLayoutX(60);
        imageView.setLayoutY(20);
        distroy.setLayoutX(250);
        distroy.setLayoutY(60);
        ProgressBar health = new ProgressBar(this.health/100);
        health.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        health.setLayoutY(20);
        health.setPrefSize(100,20);
        anchorPane.getChildren().addAll(imageView, distroy, health);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        if (!owner){
            distroy.setVisible(false);
        }
        return anchorPane;
    }
}
