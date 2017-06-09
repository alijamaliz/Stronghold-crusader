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
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Barracks.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button createSoldier = new Button("createSoldier");
        File soldier = new File("Resources/images/Humans/Soldier.png");
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
        createSoldier.setLayoutX(createSoldier.getLayoutX()+50);
        imageView.setLayoutY(imageView.getLayoutY()+200);
        anchorPane.getChildren().addAll(imageView,createSoldier,soldier1,distroy);
        return anchorPane;
    }
}
