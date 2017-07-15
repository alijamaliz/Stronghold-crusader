package StrongholdCrusader.GameObjects.Ship;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.*;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    public Pair size;
    public int speed;
    private MapTile targetTile;
    private MapTile nextTile;

    public Ship() {
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(5, 4);
        this.type = "Ship";
    }

    public Ship(MapGUI mapGUI) {
        super(mapGUI);
        this.speed = Settings.SHIP_SPEED;
        this.health = Settings.SHIP_INITIAL_HEALTH;
        this.size = new Pair(5, 4);
        this.type = "Ship";
    }

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializaAnchorPane();

        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        return anchorPane;
    }


    public void initializaAnchorPane() {
        anchorPane = new AnchorPane();
        destroy = new Button("Destroy Ship");
        transition(destroy);
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
        targetTile = tile;
        LinkedList<MapTile> path = findRoute(tiles, tiles[position.x][position.y], targetTile);
        if (path.size() != 0) {
            path.removeLast();
            this.nextTile = path.getLast();
        }
    }
}
