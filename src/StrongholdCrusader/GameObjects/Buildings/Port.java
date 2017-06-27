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
public class Port extends Building {
    public Port() {
        this.type = "Port";
        this.size = new Pair(4, 4);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Port.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        imageView.setLayoutX(170);
        distroy.setLayoutX(170);
        distroy.setLayoutY(150);
        anchorPane.getChildren().addAll(imageView, distroy);
        anchorPane.setPrefSize(500, 250);
        anchorPane.setLayoutX(20);
        anchorPane.setLayoutY(Screen.getPrimary().getBounds().getHeight()-270);
        anchorPane.setId("barracks");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");
        return anchorPane;
    }
}
