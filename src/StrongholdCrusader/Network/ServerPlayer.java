package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.Settings;

import java.net.*;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */

public class ServerPlayer {
    String playerName;
    InetAddress address;
    int port;
    LinkedList<GameObject> objects = new LinkedList<>();

    public int golds;
    public int woods;
    public int foods;


    public ServerPlayer(String playerName, InetAddress address, int port) {
        this.playerName = playerName;
        this.address = address;
        this.port = port;

        golds = Settings.INITIAL_PLAYER_GOLDS;
        foods = Settings.INITIAL_PLAYER_FOODS;
        woods = Settings.INITIAL_PLAYER_WOODS;
    }
}
