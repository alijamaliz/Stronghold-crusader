package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.Palace;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Baran on 5/29/2017.
 */
public class Game {
    public LinkedList<ServerPlayer> players;
    public LinkedList<GameObject> objects;
    public int mapId;
    private ArrayList<Pair> palacePositions;

    public Game(int mapId) {
        players = new LinkedList<>();
        objects = new LinkedList<>();
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
}
