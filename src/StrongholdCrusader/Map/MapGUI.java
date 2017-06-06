package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Buildings.Building;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.Menu;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.Serializable;

/**
 * Created by Baran on 6/3/2017.
 */
public class MapGUI implements Runnable,Serializable {
    ResourceManager resourceManager;
    Map map;

    public MapGUI(Map map) {
        this.map = map;
        resourceManager = new ResourceManager();

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getMapBackground());
        anchorPane.getChildren().add(getMapObjects());

        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(anchorPane, 600, 300);
        //Setting the title to Stage.
        Menu.stage.setTitle("Map");
        //Adding the scene to Stage
        Menu.stage.setScene(scene);

        Menu.stage.setMaximized(true);
        Menu.stage.setFullScreen(true);
        Menu.stage.show();
    }

    //Return an AnchorPane containing tile images
    public AnchorPane getMapBackground() {
        AnchorPane background = new AnchorPane();
        for (int i = 0; i < map.tiles.length; i++) {
            for (int j = 0; j < map.tiles[i].length; j++) {
                Image image = null;

                if(map.tiles[i][j] instanceof Sea)
                    image = resourceManager.getImage("Farm");
                if(map.tiles[i][j] instanceof Plain)
                    image = resourceManager.getImage("Plain1");
                if(map.tiles[i][j] instanceof Mountain)
                    image = resourceManager.getImage("Port");

                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setLayoutX(i * (image.getWidth()));
                imageView.setLayoutY(j * (image.getHeight()));
                background.getChildren().add(imageView);
            }
        }
        return background;
    }
    //Return an AnchorPane containing game object images
    public AnchorPane getMapObjects() {
        AnchorPane objects = new AnchorPane();
        for (GameObject object : map.objects) {
            Image image = null;
            if (object instanceof Building)
                image = resourceManager.getImage(object.name);
            if (object instanceof Human)
                image = resourceManager.getImage(object.name);

            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setLayoutX(object.position.x);
            imageView.setLayoutY(object.position.y);
            objects.getChildren().add(imageView);
        }
        return objects;
    }

    //Refresh game objects position and show them
    public void showMap() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().remove(1);
        anchorPane.getChildren().add(getMapObjects());

        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(anchorPane, 600, 300);
        //Setting the title to Stage.
        Menu.stage.setTitle("Map");
        //Adding the scene to Stage
        Menu.stage.setScene(scene);
    }

    //Thread for map update in each game cycle
    @Override
    public void run() {
        while (true) {
            try {
                showMap();
                Thread.sleep(1000 / Settings.FRAME_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
