package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.Settings;

import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Map {
    MapTile[][] tiles = new MapTile[Settings.MAP_WIDTH_RESOLUTION][Settings.MAP_HEIGHT_RESOLUTION];
    LinkedList<GameObject> objects = new LinkedList<>();
    MapManager mapManager;
    MapGUI gui = new MapGUI();
}