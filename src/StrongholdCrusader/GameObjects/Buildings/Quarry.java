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
public class Quarry extends Building {
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Quarry.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        anchorPane.getChildren().addAll(imageView,distroy);
        return anchorPane;
    }
}
