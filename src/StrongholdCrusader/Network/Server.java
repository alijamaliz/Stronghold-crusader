package StrongholdCrusader.Network;

import StrongholdCrusader.Network.ServerPlayer;
import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Settings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;

/**
 * Created by Baran on 5/29/2017.
 */
public class Server implements Runnable {
    Thread listenThread;
    Thread sendMapThread;
    DatagramSocket socket;
    Game game;

    public Server(int mapId) {
        game = new Game();

        try {
            socket = new DatagramSocket(Settings.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        listenThread = new Thread(this);
        listenThread.start();

        sendMapThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //sendMapToAll(new Map());
                    try {
                        Thread.sleep(1000 / Settings.FRAME_RATE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendMapThread.start();

    }

    @Override
    public void run() {
        try {
            //buffer to receive incoming data
            byte[] buffer = new byte[Settings.PACKET_MAX_SIZE];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            //2. Wait for an incoming data
            System.out.println("Server socket created. Waiting for incoming data...");
            //communication loop
            while (true) {
                socket.receive(incoming);
                byte[] data = incoming.getData();
                String packet = new String(data, 0, incoming.getLength());
                analyzePacket(packet, incoming.getAddress(), incoming.getPort());
                Thread.sleep(1000 / Settings.FRAME_RATE);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1);
    }

    private void analyzePacket(String body, InetAddress address, int port) {
        JSONParser jsonParser = new JSONParser();
        GameEvent gameEvent = new GameEvent();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(body);
            gameEvent.type = new Integer(((Long) jsonObject.get("type")).intValue());
            gameEvent.message = (String) (jsonObject.get("message"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (gameEvent.type) {
            case GameEvent.JOIN_TO_GAME: {
                //.out.println(gameEvent.message + ":" + address + ":" + port);
                if (isUsernameAvailable(gameEvent.message)) {
                    game.players.add(new ServerPlayer(gameEvent.message, address, port));
                    sendPacket("ClientPlayer " + gameEvent.message + " created!", address, port);
                } else
                    sendPacket("ClientPlayer name is already taken...", address, port);
                break;
            }
        }
    }

    private boolean sendPacket(String body, InetAddress address, int port) {
        DatagramPacket dp = new DatagramPacket(body.getBytes(), body.getBytes().length, address, port);
        try {
            socket.send(dp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendPacketForAll(String body) {
        for (ServerPlayer player : game.players) {
            sendPacket(body, player.address, player.port);
        }
    }

    public void sendMapToAll(Map map) {
        sendPacketForAll("Map");
    }

    public boolean isUsernameAvailable(String username) {
        for (ServerPlayer player : game.players) {
            if (player.playerName.equals(username))
                return false;
        }
        return true;
    }

    public String getServerIP() {
        String serverIP = "";
        try {
            serverIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return serverIP;
    }
}
