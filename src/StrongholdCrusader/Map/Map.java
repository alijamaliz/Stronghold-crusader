package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.Settings;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Map implements Serializable {
    MapTile[][] tiles;
    LinkedList<GameObject> objects;
    MapManager mapManager;
    MapGUI gui;

    public Map() {
        mapManager = new MapManager();
        tiles = mapManager.getMapById(1);
        objects = new LinkedList<>();
        gui = new MapGUI(this);
    }
}