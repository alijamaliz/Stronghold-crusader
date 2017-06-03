package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.Settings;

import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Map {
    MapTile[][] tiles;
    LinkedList<GameObject> objects;
    MapManager mapManager;
    MapGUI gui;

    public Map() {
        tiles = new MapTile[Settings.MAP_WIDTH_RESOLUTION][Settings.MAP_HEIGHT_RESOLUTION];
        objects = new LinkedList<>();
        gui = new MapGUI(this);
        mapManager = new MapManager();
    }
}