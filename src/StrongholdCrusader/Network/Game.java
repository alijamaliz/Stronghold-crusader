package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.Map.Map;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Game {
    public LinkedList<ServerPlayer> players;
    public LinkedList<GameObject> objects;

    public Game() {
        players = new LinkedList<>();
        objects = new LinkedList<>();

    }
}
