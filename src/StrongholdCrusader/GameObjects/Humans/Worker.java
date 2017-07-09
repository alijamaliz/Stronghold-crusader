package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Map.MapTile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Worker extends Human {
    public Worker()
    {
        this.type="Worker";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public Worker(MapGUI mapGUI)
    {
        super(mapGUI);
        this.type="Worker";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Worker.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        Button button = new Button("Change to Vassal");
        CheckBox checkBox = new CheckBox("Can Climb ?");
        checkBox.setLayoutX(400);
        checkBox.setLayoutY(60);
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    mapGUI.changeClimbStatus(Worker.this,checkBox.isSelected());
            }
        });
        button.setLayoutX(200);
        imageView.setLayoutX(100);
        imageView.setLayoutY(40);
        button.setLayoutY(60);
        anchorPane.getChildren().addAll(imageView,button,checkBox);
        anchorPane.setPrefSize(300,100);
        return anchorPane;
    }
    public void getResorces()
    {
        ///TODO
    }
    @Override
    public void useResources()
    {
        ///TODO
    }
}
