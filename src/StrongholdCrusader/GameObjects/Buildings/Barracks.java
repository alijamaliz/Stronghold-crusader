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
    public Barracks()
    {
        this.type="Barracks";
    }
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction() {
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

            }
        });
        createSoldier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        buildingImage.setLayoutX(20);
        buildingImage.setLayoutY(buildingImage.getLayoutY()+40);
        distroy.setLayoutX(20);
        distroy.setLayoutY(210);
        soldierImage.setLayoutX(300);
        soldierImage.setLayoutY(100);
        createSoldier.setLayoutX(270);
        createSoldier.setLayoutY(210);
        anchorPane.getChildren().addAll(buildingImage,createSoldier,soldierImage,distroy);
        anchorPane.setPrefSize(500,250);
        return anchorPane;
    }
}
