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

/**
 * Created by Baran on 6/3/2017.
 */
public class MapGUI implements Runnable {
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

    public AnchorPane getMapBackground() {
        AnchorPane background = new AnchorPane();
        for (int i = 0; i < map.tiles.length; i++) {
            for (int j = 0; j < map.tiles[i].length; j++) {
                Image image = null;

                if(map.tiles[i][j] instanceof Sea)
                    image = resourceManager.getBuilding("Farm");
                if(map.tiles[i][j] instanceof Plain)
                    image = resourceManager.getBuilding("Market");
                if(map.tiles[i][j] instanceof Mountain)
                    image = resourceManager.getBuilding("Port");

                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setLayoutX(i * (resourceManager.getBuilding("Farm").getWidth()));
                imageView.setLayoutY(j * (resourceManager.getBuilding("Farm").getHeight()));
                background.getChildren().add(imageView);
            }
        }
        return background;
    }
    public AnchorPane getMapObjects() {
        AnchorPane objects = new AnchorPane();
        for (GameObject object : map.objects) {
            Image image = null;
            if (object instanceof Building)
                image = resourceManager.getBuilding(object.name);
            if (object instanceof Human)
                image = resourceManager.getHuman(object.name);

            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setLayoutX(object.position.x);
            imageView.setLayoutY(object.position.y);
            objects.getChildren().add(imageView);
        }
        return objects;
    }

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
