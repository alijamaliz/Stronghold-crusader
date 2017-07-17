package StrongholdCrusader.GameObjects.Ship;

import StrongholdCrusader.GameObjects.Buildings.Port;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.*;
import StrongholdCrusader.Network.Server;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Alireza on 7/13/2017.
 */
public class Ship extends GameObject {
    AnchorPane anchorPane;
    Button destroy;
    Text foodsText;
    public Pair size;
    public int speed;
    private MapTile targetTile;
    private MapTile nextTile;
    public int collectedFood;
    public Pair firstPosition;
    private int speedHandler;
    public shipMode mode = shipMode.GOING_TO_TARGET;

    public enum shipMode {
        GOING_TO_TARGET,
        COLLECTING_FOOD,
        RETURNING_BACK
    }

    public Ship(Pair firstPosition) {
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(3, 3);
        this.type = "Ship";
        this.firstPosition = firstPosition;
        collectedFood = 0;
    }

    public Ship() {
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(3, 3);
        this.type = "Ship";
        collectedFood = 0;
    }

    public Ship(MapGUI mapGUI) {
        super(mapGUI);
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(3, 3);
        setImage(ResourceManager.getImage("Ship"));
        this.type = "Ship";
        collectedFood = 0;
    }

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializaAnchorPane();

        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mapGUI.removeShip(id);
            }
        });

        return anchorPane;
    }


    public void initializaAnchorPane() {
        anchorPane = new AnchorPane();
        destroy = new Button("Destroy Ship");
        foodsText =  new Text();
        transition(destroy);
        foodsText.setText("Collected foods: " + String.valueOf(collectedFood));
        destroy.setLayoutX(100);
        destroy.setLayoutY(40);
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH, Settings.MENUS_ANCHORPANE_HEIGHT);
        anchorPane.getChildren().addAll(destroy);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/building.css");

    }

    public void transition(Node button) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(100), button);
        transition.setAutoReverse(true);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1);
                transition.setToX(1.1);
                transition.setFromY(1);
                transition.setToY(1.1);
                transition.play();
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1.1);
                transition.setToX(1);
                transition.setFromY(1.1);
                transition.setToY(1);
                transition.play();
            }
        });
    }
    private static LinkedList<MapTile> adjacentList(MapTile[][] tiles, MapTile tile) {
        LinkedList<MapTile> adjacents = new LinkedList<>();
        for (int i = tile.position.x - 1; i < tile.position.x + 2; i++) {
            for (int j = tile.position.y - 1; j < tile.position.y + 2; j++) {
                if ((i == tile.position.x && j != tile.position.y) || (j == tile.position.y && i != tile.position.x)) // Not adding corner tiles
                {
                    try {
                        MapTile tmp = tiles[i][j];
                        if (!tmp.filled) //for climbers
                        {
                            if (tmp instanceof Sea) {
                                adjacents.add(tmp);
                            }
                        }
                    } catch (Exception e) {
                        ///Do Nothing becuase its Null pionter and unwanted nodes
                    }
                }
            }
        }
        return adjacents;
    }
    private LinkedList<MapTile> findRoute(MapTile[][] tiles, MapTile start, MapTile end) ///BFS codes go here
    {
        HashMap<MapTile, Boolean> visited = new HashMap<>(); //every tile is visited or not
        HashMap<MapTile, MapTile> edgeTo = new HashMap<>(); // stores where the tile comes from
        LinkedList<MapTile> path = new LinkedList<>();
        Queue<MapTile> queue = new LinkedList();
        MapTile currunt = start;
        queue.add(currunt);
        visited.put(currunt, true);
        while (!queue.isEmpty()) {
            currunt = queue.remove();
            if (currunt.equals(end)) {
                break;
            } else {
                for (MapTile mapTile : adjacentList(tiles, currunt)) {
                    if (!visited.containsKey(mapTile)) {
                        queue.add(mapTile);
                        visited.put(mapTile, true);
                        edgeTo.put(mapTile, currunt);
                    }
                }
            }
        }
        if (!currunt.equals(end)) // runs when there is not a way and return an empty LinkedList
        {
            return path;
        }
        for (MapTile tile = end; tile != null; tile = edgeTo.get(tile)) // adding tiles to LinkedList (reversely)
        {
            path.addLast(tile);
        }
        return path;
    }
    public void goToTile(MapTile[][] tiles, MapTile tile) {
        System.out.println("Goto Tile");
        targetTile = tile;
        LinkedList<MapTile> path = findRoute(tiles, tiles[position.x][position.y], targetTile);
        System.out.println("Route length: " + path.size());
        if (path.size() > 1) {
            path.removeLast();
            this.nextTile = path.getLast();
        }
    }
    public void updatePosition(Server server, MapTile[][] tiles) {

        if (speedHandler == Settings.SEND_DATA_RATE / speed) {
            System.out.println("Update");
            if (nextTile != null) {
                tiles[this.position.x][this.position.y].filled = false;
                this.position = nextTile.position;
                tiles[this.position.x][this.position.y].filled = true;
                if (!tiles[position.x][position.y].equals(targetTile)) {
                    LinkedList<MapTile> path = findRoute(tiles, tiles[position.x][position.y], targetTile);
                    if (path.size() != 0) {
                        path.removeLast();
                        this.nextTile = path.getLast();
                    }
                } else {
                    if (mode == shipMode.GOING_TO_TARGET)
                        mode = shipMode.COLLECTING_FOOD;
                    if (mode == shipMode.RETURNING_BACK) {
                        server.emptyShipAndSendThatAgain(id);
                    }
                    nextTile = null;
                }
            }
            speedHandler = 0;
        }
        speedHandler++;
    }
}
