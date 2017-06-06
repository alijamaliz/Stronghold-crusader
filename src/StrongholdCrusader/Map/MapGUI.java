package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Buildings.Building;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Menu;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.Serializable;
import java.util.NavigableMap;

/**
 * Created by Baran on 6/3/2017.
 */
public class MapGUI implements Runnable, Serializable {
    ResourceManager resourceManager;
    Pair viewOffset;
    Map map;

    String navigationLR;
    String navigationUD;

    AnchorPane anchorPane;
    Scene scene;

    public MapGUI(Map map) {
        this.map = map;
        resourceManager = new ResourceManager();
        viewOffset = new Pair(0, 0);

        navigationLR = "";
        navigationUD = "";

        anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getMapBackground());
        anchorPane.getChildren().add(getMapObjects());

        //Creating a Scene by passing the group object, height and width
        scene = new Scene(anchorPane, 600, 300);
        //Offset
        anchorPane.setTranslateX(viewOffset.x);
        anchorPane.setTranslateY(viewOffset.y);
        //Setting the title to Stage.
        Menu.stage.setTitle("Map");
        //Adding the scene to Stage
        Menu.stage.setScene(scene);

        Menu.stage.setMaximized(true);
        Menu.stage.setFullScreen(true);
        Menu.stage.show();

        //Arrow keys navigating
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode());
                if (event.getCode().getName().equals("Left") && !navigationLR.contains("L"))
                    navigationLR = "L" + navigationLR;
                if (event.getCode().getName().equals("Right") && !navigationLR.contains("R"))
                    navigationLR = "R" + navigationLR;
                if (event.getCode().getName().equals("Up") && !navigationUD.contains("U"))
                    navigationUD = "U" + navigationUD;
                if (event.getCode().getName().equals("Down") && !navigationUD.contains("D"))
                    navigationUD = "D" + navigationUD;
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(event.getCode().getName());
                if (event.getCode().getName().equals("Left"))
                    navigationLR = navigationLR.replace("L", "");
                if (event.getCode().getName().equals("Right"))
                    navigationLR = navigationLR.replace("R", "");
                if (event.getCode().getName().equals("Up"))
                    navigationUD = navigationUD.replace("U", "");
                if (event.getCode().getName().equals("Down"))
                    navigationUD = navigationUD.replace("D", "");
            }
        });

        //Mouse Navigating
        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getScreenX() + ":" + event.getScreenY());
                if (event.getScreenX() < Settings.MOUSE_MAP_NAVIGATION_MARGIN)
                    navigationLR = "L" + navigationLR;
                else
                    navigationLR = navigationLR.replace("L", "");

                if (event.getScreenX() > scene.getWidth() - Settings.MOUSE_MAP_NAVIGATION_MARGIN)
                    navigationLR = "R" + navigationLR;
                else
                    navigationLR = navigationLR.replace("R", "");


                if (event.getScreenY() < Settings.MOUSE_MAP_NAVIGATION_MARGIN)
                    navigationUD = "U" + navigationUD;
                else
                    navigationUD = navigationUD.replace("U", "");

                if (event.getScreenY() > scene.getHeight() - Settings.MOUSE_MAP_NAVIGATION_MARGIN)
                    navigationUD = "D" + navigationUD;
                else
                    navigationUD = navigationUD.replace("D", "");
            }
        });
    }

    private void changeViewOffset() {
        int mapWidth = Settings.MAP_WIDTH_RESOLUTION * (int) resourceManager.getImage("Plain1").getWidth();
        int mapHeight = Settings.MAP_HEIGHT_RESOLUTION * (int) resourceManager.getImage("Plain1").getHeight();
        if (navigationLR.length() != 0) {
            if (navigationLR.charAt(0) == 'R') {
                if (viewOffset.x - Settings.MAP_NAVIGATION_SPEED > -1 * (mapWidth - Settings.MAP_NAVIGATION_SPEED - scene.getWidth()))
                    viewOffset.x -= Settings.MAP_NAVIGATION_SPEED;
            }
            if (navigationLR.charAt(0) == 'L') {
                if (viewOffset.x + Settings.MAP_NAVIGATION_SPEED < Settings.MAP_NAVIGATION_SPEED)
                    viewOffset.x += Settings.MAP_NAVIGATION_SPEED;
            }
        }
        if (navigationUD.length() != 0) {
            if (navigationUD.charAt(0) == 'D') {
                if (viewOffset.y - Settings.MAP_NAVIGATION_SPEED > -1 * (mapHeight - Settings.MAP_NAVIGATION_SPEED - scene.getHeight()))
                    viewOffset.y -= Settings.MAP_NAVIGATION_SPEED;
            }
            if (navigationUD.charAt(0) == 'U') {
                if (viewOffset.y + Settings.MAP_NAVIGATION_SPEED < Settings.MAP_NAVIGATION_SPEED)
                    viewOffset.y += Settings.MAP_NAVIGATION_SPEED;
            }
        }
    }

    //Return an AnchorPane containing tile images
    public AnchorPane getMapBackground() {
        AnchorPane background = new AnchorPane();
        for (int i = 0; i < map.tiles.length; i++) {
            for (int j = 0; j < map.tiles[i].length; j++) {
                Image image = null;

                if (map.tiles[i][j] instanceof Sea)
                    image = resourceManager.getImage("Farm");
                if (map.tiles[i][j] instanceof Plain)
                    image = resourceManager.getImage("Plain1");
                if (map.tiles[i][j] instanceof Mountain)
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
        //AnchorPane anchorPane = new AnchorPane();
        //anchorPane.getChildren().add(getMapBackground());

        //anchorPane.getChildren().remove(1);
        //anchorPane.getChildren().add(getMapObjects());

        //Creating a Scene by passing the group object, height and width
        //Offset
        anchorPane.setTranslateX(viewOffset.x);
        anchorPane.setTranslateY(viewOffset.y);
        //Setting the title to Stage.
        //Menu.stage.setTitle("Map");
        //Adding the scene to Stage
        //Menu.stage.setScene(scene);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ;
                Menu.stage.setScene(scene);
            }
        });


    }

    //Thread for map update in each game cycle
    @Override
    public void run() {
        while (true) {
            try {
                changeViewOffset();
                //System.out.println(navigationLR + ":" + navigationUD);
                showMap();
                Thread.sleep(1000 / Settings.FRAME_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
