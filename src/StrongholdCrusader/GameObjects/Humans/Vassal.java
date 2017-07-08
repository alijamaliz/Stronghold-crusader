package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Map.MapTile;
import StrongholdCrusader.MenuGUI;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Vassal extends Human {
    public Vassal()
    {
        this.type="Vassal";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public Vassal(MapGUI mapGUI) {
        super(mapGUI);
        this.type="Vassal";
        this.speed = 1;
        this.zone=3;
        this.power=20;
        this.health=500;
    }
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Vassal.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        anchorPane.getChildren().addAll(imageView);
        anchorPane.setPrefSize(300,100);
        return anchorPane;
    }
    @Override
    public void useResources() {

    }
}
