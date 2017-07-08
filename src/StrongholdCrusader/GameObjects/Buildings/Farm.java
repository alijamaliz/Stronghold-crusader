package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Farm extends Building {
    public Farm() {
        this.type = "Farm";
        this.size = new Pair(4, 4);
    }

    public Farm(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Farm";
        this.size = new Pair(4, 4);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction() {

        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Farm.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Farm.this.mapGUI.removeBuildings(Farm.this);
            }
        });
        imageView.setLayoutX(60);
        imageView.setLayoutY(20);
        distroy.setLayoutX(250);
        distroy.setLayoutY(60);
        anchorPane.getChildren().addAll(imageView, distroy);
        anchorPane.setId("building");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        return anchorPane;
    }
}
