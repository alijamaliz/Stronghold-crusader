package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
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

            }
        });
        imageView.setLayoutX(140);
        distroy.setLayoutX(140);
        distroy.setLayoutY(150);
        anchorPane.getChildren().addAll(imageView, distroy);
        anchorPane.setPrefSize(400, 250);
        anchorPane.setId("farm");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        return anchorPane;
    }
}
