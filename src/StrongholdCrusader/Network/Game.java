package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.Building;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.*;
import StrongholdCrusader.Settings;

import java.util.*;

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

    public void addHumanToMap(Human human) {
        objects.add(human);
        tiles[human.position.x][human.position.y].filled = true;
    }

    public boolean humanCanCreate(Human human) {
        if (human.position.x > Settings.MAP_WIDTH_RESOLUTION || human.position.y > Settings.MAP_HEIGHT_RESOLUTION)
            return false;
        return !tiles[human.position.x][human.position.y].filled;
    }

    public void addBuildingToMap(Building building) {
        objects.add(building);
        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {
            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {
                tiles[i][j].filled = true;
            }
        }
    }
    public void removeBuilding(Building building){
        objects.remove(building);
    }

    public boolean buildingCanCreate(Building building) {
        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)
            return false;
        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {
            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {
                if (tiles[i][j].filled)
                    return false;
            }
        }
        return true;
    }

    public GameObject getGameObjectById(int id) {
        for (GameObject object : objects) {
            if (object.id == id)
                return object;
        }
        return null;
    }
}
