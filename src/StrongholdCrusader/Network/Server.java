package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapManager;
import StrongholdCrusader.Settings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.util.Random;

/**
 * Created by Baran on 5/29/2017.
 */
public class Server implements Runnable {
    Thread listenThread;
    Thread sendMapObjectsThread;
    DatagramSocket socket;
    Game game;

    public Server(int mapId) {
        game = new Game(mapId);

        try {
            socket = new DatagramSocket(Settings.SERVER_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        listenThread = new Thread(this);
        listenThread.start();

        sendMapObjectsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    sendMapObjectsToAll();
                    try {
                        Thread.sleep(1000 / Settings.FRAME_RATE * 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                String username = gameEvent.message;
                if (isUsernameAvailable(username)) {
                    game.players.add(new ServerPlayer(username, address, port));
                    //Send OK result for client
                    GameEvent createGameEvent = new GameEvent(GameEvent.USER_SUCCESSFULLY_CREATED, "ClientPlayer " + username + " created!");
                    sendPacket(createGameEvent.getJSON(), address, port);
                    //Send join alert for all clients
                    GameEvent joinGameEvent = new GameEvent(GameEvent.USER_JOINED_TO_NETWORK, username + "," + address.getHostAddress());
                    sendPacketForAll(joinGameEvent.getJSON());
                } else {
                    //Send duplicate result for client
                    GameEvent duplicateGameEvent = new GameEvent(GameEvent.DUPLICATE_USERNAME, "ClientPlayer name is already taken...");
                    sendPacket(duplicateGameEvent.getJSON(), address, port);
                }
                break;
            }
            case GameEvent.START_GAME: {
                GameEvent startGameEvent = new GameEvent(GameEvent.START_GAME, String.valueOf(game.mapId));
                sendPacketForAll(startGameEvent.getJSON());
                //sendMapToAll();
                sendMapObjectsThread.start();
                break;
            }
            case GameEvent.WOOD_CUTTER_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                WoodCutter woodCutter = new WoodCutter();
                woodCutter.position = new Pair(x, y);
                woodCutter.id = generateNewID();
                woodCutter.ownerName = getSenderPlayerName(address);
                game.objects.add(woodCutter);
                break;
            }
            case GameEvent.BARRACKS_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Barracks barracks = new Barracks();
                barracks.position = new Pair(x, y);
                barracks.id = generateNewID();
                barracks.ownerName = getSenderPlayerName(address);
                game.objects.add(barracks);
                break;
            }
            case GameEvent.FARM_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Farm farm = new Farm();
                farm.position = new Pair(x, y);
                farm.id = generateNewID();
                farm.ownerName = getSenderPlayerName(address);
                game.objects.add(farm);
                break;
            }
            case GameEvent.MARKET_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Market market = new Market();
                market.position = new Pair(x, y);
                market.id = generateNewID();
                market.ownerName = getSenderPlayerName(address);
                game.objects.add(market);
                break;
            }
            case GameEvent.QUARRAY_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Quarry quarry = new Quarry();
                quarry.position = new Pair(x, y);
                quarry.id = generateNewID();
                quarry.ownerName = getSenderPlayerName(address);
                game.objects.add(quarry);
                break;
            }
            case GameEvent.PORT_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Port portBuilding = new Port();
                portBuilding.position = new Pair(x, y);
                portBuilding.id = generateNewID();
                portBuilding.ownerName = getSenderPlayerName(address);
                game.objects.add(portBuilding);
                break;
            }
        }
    }

    private String getSenderPlayerName(InetAddress address) {
        for (ServerPlayer player : game.players) {
            if (player.address.getHostAddress().equals(address.getHostAddress()))
                return player.playerName;
        }
        return "Unknown";
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

    public void sendMapToAll() {
        //String mapString = MapManager.mapTilesToJSON(game.mapId).toJSONString();
        GameEvent mapGameEvent = new GameEvent(GameEvent.MAP_ID, String.valueOf(game.mapId));
        sendPacketForAll(mapGameEvent.getJSON());
    }

    public void sendMapObjectsToAll() {
        String objects = MapManager.mapObjectsToJSON(game.objects).toJSONString();
        GameEvent gameEvent = new GameEvent(GameEvent.MAP_OBJECTS, objects);
        sendPacketForAll(gameEvent.getJSON());
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

    private int generateNewID() {
        int id = new Random().nextInt(9999) + 1000;
        while (isDuplicateId(id)) {
            id = new Random().nextInt(9999) + 1000;
        }
        return id;
    }

    private boolean isDuplicateId(int id) {
        for (GameObject object : game.objects) {
            if (object.id == id)
                return true;
        }
        return false;
    }
}
