package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
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
                    updateMapObjects();
                    sendMapObjectsToAll();
                    sendResourcesForAll();
                    try {
                        Thread.sleep(1000 / Settings.SEND_DATA_RATE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void updateMapObjects() {
        for (GameObject object : game.objects) {
            if (object instanceof Human) {
                Human human = (Human) object;
                human.updatePosition(game.tiles);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(1);
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

                    //Send previous players for new player
                    for (ServerPlayer player : game.players) {
                        GameEvent joinGameEvent = new GameEvent(GameEvent.USER_JOINED_TO_NETWORK, player.playerName + "," + player.address.getHostAddress());
                        sendPacket(joinGameEvent.getJSON(), address, port);
                    }

                    game.players.add(new ServerPlayer(username, address, port));

                    //Create new user Palace
                    Palace palace = new Palace();
                    palace.position = game.getRandomPalacePosition();
                    palace.health = 100;
                    palace.id = generateNewID();
                    palace.ownerName =getSenderPlayerByAddress(address).playerName;
                    game.addBuildingToMap(palace);

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
                sendFocusOnPalacePacketForAll();
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
                woodCutter.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(woodCutter))
                    game.addBuildingToMap(woodCutter);
                break;
            }
            case GameEvent.BARRACKS_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Barracks barracks = new Barracks();
                barracks.position = new Pair(x, y);
                barracks.id = generateNewID();
                barracks.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(barracks))
                    game.addBuildingToMap(barracks);
                break;
            }
            case GameEvent.FARM_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Farm farm = new Farm();
                farm.position = new Pair(x, y);
                farm.id = generateNewID();
                farm.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(farm))
                    game.addBuildingToMap(farm);
                break;
            }
            case GameEvent.MARKET_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Market market = new Market();
                market.position = new Pair(x, y);
                market.id = generateNewID();
                market.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(market))
                    game.addBuildingToMap(market);
                break;
            }
            case GameEvent.QUARRAY_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Quarry quarry = new Quarry();
                quarry.position = new Pair(x, y);
                quarry.id = generateNewID();
                quarry.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(quarry))
                    game.addBuildingToMap(quarry);
                break;
            }
            case GameEvent.PORT_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Port portBuilding = new Port();
                portBuilding.position = new Pair(x, y);
                portBuilding.id = generateNewID();
                portBuilding.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.buildingCanCreate(portBuilding))
                    game.addBuildingToMap(portBuilding);
                break;
            }
            case GameEvent.WORKER_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Worker worker = new Worker();
                worker.position = new Pair(x, y);
                worker.id = generateNewID();
                worker.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.humanCanCreate(worker))
                    game.addHumanToMap(worker);
                break;
            }
            case GameEvent.VASSEL_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Vassal vassal = new Vassal();
                vassal.position = new Pair(x, y);
                vassal.id = generateNewID();
                vassal.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.humanCanCreate(vassal))
                    game.addHumanToMap(vassal);
                break;
            }
            case GameEvent.SOLDIER_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Soldier soldier = new Soldier();
                soldier.position = new Pair(x, y);
                soldier.id = generateNewID();
                soldier.ownerName = getSenderPlayerByAddress(address).playerName;
                if (game.humanCanCreate(soldier))
                    game.addHumanToMap(soldier);
                break;
            }
            case GameEvent.MOVE_HUMAN: {
                String[] args = gameEvent.message.split(":");
                int humanId = Integer.parseInt(args[0]);
                int x = Integer.parseInt(args[1]);
                int y = Integer.parseInt(args[2]);
                moveHuman(humanId, new Pair(x, y));
                break;
            }
            case GameEvent.DISTROY_BUILDING: {
                int id = Integer.parseInt(gameEvent.message);
                game.removeBuilding((Building) game.getGameObjectById(id));
                GameEvent createGameEvent = new GameEvent(GameEvent.DISTROY_BUILDING, gameEvent.message);
                sendPacket(createGameEvent.getJSON(), address, port);
                break;
            }
            case GameEvent.CHANGE_HUMAN_CLIMB: {
                String[] args = gameEvent.message.split(":");
                int humanId = Integer.parseInt(args[0]);
                boolean status;
                if (args[1].equals("true"))
                    status = true;
                else
                    status = false;
                changeHumanClimb(humanId, status);
                break;
            }
            case GameEvent.BUY_RESOURCE : {
                if(gameEvent.message.equals("wood"))
                {
                    getSenderPlayerByAddress(address).golds -= 5;
                    getSenderPlayerByAddress(address).woods += 5;
                }
                if(gameEvent.message.equals("food"))
                {
                    getSenderPlayerByAddress(address).golds -= 10;
                    getSenderPlayerByAddress(address).foods += 5;
                }
                break;
            }
            case GameEvent.SELL_RESOURCE : {
                if(gameEvent.message.equals("wood"))
                {
                    getSenderPlayerByAddress(address).golds += 3;
                    getSenderPlayerByAddress(address).woods -= 5;
                }
                if(gameEvent.message.equals("food"))
                {
                    getSenderPlayerByAddress(address).golds += 7;
                    getSenderPlayerByAddress(address).foods -= 5;
                }
                break;
            }
        }
    }

    private void changeHumanClimb(int humanId, boolean status) {
        Human human = (Human) game.getGameObjectById(humanId);
        human.canClimb = status;
    }

    private void sendFocusOnPalacePacketForAll() {
        for (ServerPlayer player : game.players) {
            Palace palace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(player.playerName));
            GameEvent palaceFocus = new GameEvent(GameEvent.FOCUS_ON_BUILDING, palace.position.x + ":" + palace.position.y);
            sendPacket(palaceFocus.getJSON(), player.address, player.port);
        }
    }

    private void sendResourcesForAll() {
        for (ServerPlayer player : game.players) {
            GameEvent resourcesEvent = new GameEvent(GameEvent.RESOURCES, player.golds + ":" + player.foods + ":" + player.woods);
            sendPacket(resourcesEvent.getJSON(), player.address, player.port);
        }
    }

    private int getPalaceIdByPlayerName(String playerName) {
        for (GameObject object : game.objects) {
            if (object instanceof Palace && object.ownerName.equals(playerName))
                return object.id;
        }
        return 0;
    }

    private void moveHuman(int humanId, Pair pair) {
        Human human = (Human) game.getGameObjectById(humanId);
        if (human != null) {
            human.goToTile(game.tiles, game.tiles[pair.x][pair.y]);
        }
    }

    private ServerPlayer getSenderPlayerByAddress(InetAddress address) {
        for (ServerPlayer player : game.players) {
            if (player.address.getHostAddress().equals(address.getHostAddress()))
                return player;
        }
        return null;
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
