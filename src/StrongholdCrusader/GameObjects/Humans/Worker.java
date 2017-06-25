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
public class Worker extends Human {
    public Worker()
    {
        this.type="Worker";
    }
    public AnchorPane anchorPane;
    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Worker.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        Button button = new Button("Change to Vassal");
        button.setLayoutX(button.getLayoutX()+70);
        button.setLayoutY(30);
        anchorPane.getChildren().addAll(imageView,button);
        anchorPane.setPrefSize(300,100);
        return anchorPane;
    }

    @Override
    public LinkedList<MapTile> territory(Map map, MapTile tile) {
        ///TODO
        return null;
    }
    public void getResorces()
    {
        ///TODO
    }
    public void useResources()
    {
        ///TODO
    }
}
