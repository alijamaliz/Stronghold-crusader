package StrongholdCrusader.Map;

import StrongholdCrusader.Menu;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Baran on 6/3/2017.
 */
public class MapGUI {
    ResourceManager resourceManager;

    public MapGUI() {
        resourceManager = new ResourceManager();

        //creating a Group object
        //Group group = new Group();
        AnchorPane anchorPane = new AnchorPane();
        /*for (int i = 0; i < Settings.MAP_WIDTH_RESOLUTION; i++) {
            for (int j = 0; j < Settings.MAP_HEIGHT_RESOLUTION; j++) {
                ImageView imageView = new ImageView();
                imageView.setImage(ResourceManager.getBuilding("Farm"));
                imageView.setLayoutX(i * (ResourceManager.getBuilding("Farm").getWidth()));
                imageView.setLayoutY(j * (ResourceManager.getBuilding("Farm").getHeight()));
                anchorPane.getChildren().add(imageView);
            }
        }*/

        //Creating a Scene by passing the group object, height and width

        Scene scene = new Scene(anchorPane, 600, 300);
        //setting color to the scene
        scene.setFill(Color.BROWN);
        //Setting the title to Stage.
        Menu.stage.setTitle("Sample Application");
        //Adding the scene to Stage
        Menu.stage.setScene(scene);
        //Displaying the contents of the stage


        Menu.stage.setMaximized(true);
        Menu.stage.setFullScreen(true);
        Menu.stage.show();
    }
}
