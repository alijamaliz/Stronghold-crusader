package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.Building;
import StrongholdCrusader.GameObjects.Buildings.Palace;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapManager;
import StrongholdCrusader.Map.MapTile;
import StrongholdCrusader.Settings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Baran on 5/29/2017.
 */
public class Game {
    public LinkedList<ServerPlayer> players;
    public LinkedList<GameObject> objects;
    public MapTile[][] tiles;
    public int mapId;
    private ArrayList<Pair> palacePositions;

    public Game(int mapId) {
        players = new LinkedList<>();
        objects = new LinkedList<>();
        tiles = MapManager.getMapTilesById(mapId);
        this.mapId = mapId;
        palacePositions = MapManager.getMapPalacePositionsById(mapId);
    }

    public Pair getRandomPalacePosition() {
        Random random = new Random();
        int index = random.nextInt(palacePositions.size());
        Pair palacePosition = palacePositions.get(index);
        palacePositions.remove(index);
        return palacePosition;
    }

    public void addBuildingToMap(Building building) {
        objects.add(building);
        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {
            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {
                tiles[i][j].filled = true;
            }
        }
    }

    public boolean buildingCanCreate (Building building) {
        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)
            return false;
        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {
            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {
                if(tiles[i][j].filled)
                    return false;
            }
        }
        return true;
    }
}
