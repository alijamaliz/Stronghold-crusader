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
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.Serializable;
/**
 * Created by Baran on 6/3/2017.
 */
public class MapGUI implements Runnable, Serializable {
    private ResourceManager resourceManager;
    private Pair viewOffset;
    private Map map;

    private String navigationLR, navigationUD;

    private AnchorPane anchorPane;
    private Scene scene;

    public MapGUI(Map map) {
        this.map = map;
        resourceManager = new ResourceManager();
        viewOffset = new Pair(0, 0);

        navigationLR = "";
        navigationUD = "";

        anchorPane = new AnchorPane();
        anchorPane.getChildren().add(getMapBackground());
        anchorPane.getChildren().add(getMapObjects());

        scene = new Scene(anchorPane);
        anchorPane.setTranslateX(viewOffset.x);
        anchorPane.setTranslateY(viewOffset.y);
        Menu.stage.setTitle("Map");
        Menu.stage.setScene(scene);
        Menu.stage.setMaximized(true);
        Menu.stage.setFullScreen(true);
        Menu.stage.show();

        //Arrow keys navigating
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
                    image = resourceManager.getImage("Sea");
                if (map.tiles[i][j] instanceof Plain)
                    image = resourceManager.getImage("Plain1");
                if (map.tiles[i][j] instanceof Mountain)
                    image = resourceManager.getImage("Mountain");

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
        //anchorPane.getChildren().add(getMapBackground());

        //anchorPane.getChildren().remove(1);
        //anchorPane.getChildren().add(getMapObjects());
        //Offset
        anchorPane.setTranslateX(viewOffset.x);
        anchorPane.setTranslateY(viewOffset.y);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
                showMap();
                Thread.sleep(1000 / Settings.FRAME_RATE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void focusOnGameObject(GameObject gameObject) {
        viewOffset.x = -1 * (int)(gameObject.position.x - scene.getWidth());
        viewOffset.y = -1 * (int)(gameObject.position.y - scene.getHeight());
    }
}
