package StrongholdCrusader.GameObjects.Buildings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Barracks extends Building {
    AnchorPane anchorPane;
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/Buildings/Barracks.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button createSoldier = new Button("createSoldier");
        File soldier = new File("Resources/Humans/Soldier.png");
        ImageView soldier1 = new ImageView(soldier.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        createSoldier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        anchorPane.getChildren().addAll(imageView,createSoldier,soldier1,distroy);
        return anchorPane;
    }
}
