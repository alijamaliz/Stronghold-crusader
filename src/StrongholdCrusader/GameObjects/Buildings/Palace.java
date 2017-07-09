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
public class Palace extends Building {
    public Palace() {
        this.type = "Palace";
        this.size = new Pair(6, 6);
    }

    public Palace(MapGUI mapGUI) {
        super(mapGUI);
        this.type = "Palace";
        this.size = new Pair(6, 6);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction(boolean owner) {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Palace.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button createVassal = new Button("Create Vassal");
        Button createWorker = new Button("Create Worker");
        createVassal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Palace.this.position.x - 1;
                int y = Palace.this.position.y - 1;
                Palace.this.mapGUI.createHuman("Vassal", new Pair(x, y));
            }
        });
        createWorker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int x = Palace.this.position.x + Palace.this.size.x;
                int y = Palace.this.position.y + Palace.this.size.y;
                Palace.this.mapGUI.createHuman("Worker", new Pair(x, y));
            }
        });
        imageView.setLayoutX(40);
        imageView.setLayoutY(-20);
        createVassal.setLayoutX(350);
        createWorker.setLayoutX(550);
        createVassal.setLayoutY(60);
        createWorker.setLayoutY(60);
        anchorPane.getChildren().addAll(imageView, createVassal, createWorker);
        anchorPane.setPrefSize(550, 250);
        anchorPane.setId("building");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        if (!owner){
            createVassal.setVisible(false);
            createWorker.setVisible(false);
        }
        return anchorPane;
    }
}
