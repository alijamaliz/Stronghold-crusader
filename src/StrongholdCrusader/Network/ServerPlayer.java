package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.GameObject;

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

        golds = 500;
        foods = 1000;
        woods = 0;
    }
}
