package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
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
    public Palace() {
        this.type = "Palace";
        this.size = new Pair(6, 6);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Palace.png");
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
        imageView.setLayoutX(40);
        imageView.setLayoutY(40);
        createVassal.setLayoutX(200);
        createWorker.setLayoutX(350);
        createVassal.setLayoutY(100);
        createWorker.setLayoutY(100);
        anchorPane.getChildren().addAll(imageView, createVassal, createWorker);
        anchorPane.setPrefSize(500, 250);
        return anchorPane;
    }
}
