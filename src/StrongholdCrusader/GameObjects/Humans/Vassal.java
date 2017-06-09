package StrongholdCrusader.GameObjects.Humans;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Vassal extends Human {
    AnchorPane anchorPane;
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Vassal.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        anchorPane.getChildren().addAll(imageView);
        return anchorPane;
    }
}
