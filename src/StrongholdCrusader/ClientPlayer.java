package StrongholdCrusader;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Network.Client;

/**
 * Created by Baran on 6/3/2017.
 */
public class ClientPlayer {
    String username;
    Client client;
    Map map;

    public ClientPlayer(String username, String serverIP) {
        this.username = username;
        client = new Client(serverIP);
        client.sendJoinRequest(username);
        map = new Map();
    }
}
