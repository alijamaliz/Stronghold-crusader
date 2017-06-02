package StrongholdCrusader.Network;

import java.net.*;

/**
 * Created by Baran on 5/29/2017.
 */

public class Player {
    String playerName;
    InetAddress address;
    int port;

    public Player(String playerName, InetAddress address, int port) {
        this.playerName = playerName;
        this.address = address;
        this.port = port;
    }
}
