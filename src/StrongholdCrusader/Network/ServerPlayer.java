package StrongholdCrusader.Network;

import StrongholdCrusader.Settings;

import java.net.InetAddress;

/**
 * Created by Baran on 5/29/2017.
 */

class ServerPlayer {
    String playerName;
    InetAddress address;
    int port;

    int golds;
    int woods;
    int foods;

    ServerPlayer(String playerName, InetAddress address, int port) {
        this.playerName = playerName;
        this.address = address;
        this.port = port;

        golds = Settings.INITIAL_PLAYER_GOLDS;
        foods = Settings.INITIAL_PLAYER_FOODS;
        woods = Settings.INITIAL_PLAYER_WOODS;
    }
}
