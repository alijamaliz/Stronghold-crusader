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
public class Palace extends Building {
    AnchorPane anchorPane;
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/Buildings/Palace.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button createVassal = new Button("Create Vassal");
        Button createWorker = new Button("Create Worker");
        createVassal.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        createWorker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        anchorPane.getChildren().addAll(imageView,createVassal,createWorker);
        return anchorPane;
    }
}
