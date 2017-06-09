package StrongholdCrusader.GameObjects.Humans;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Worker extends Human {
    AnchorPane anchorPane;
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File file = new File("Resources/images/Humans/Worker.png");
        ImageView imageView = new ImageView(file.toURI().toString());
        Button button = new Button("toVassal");
        anchorPane.getChildren().addAll(imageView,button);
        return anchorPane;
    }
}
