package StrongholdCrusader.GameObjects.Humans;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapTile;
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
    public LinkedList<MapTile> territory(Map map, MapTile tile) {
        ///TODO
        return null;
    }

    @Override
    public void useResources() {

    }
}
