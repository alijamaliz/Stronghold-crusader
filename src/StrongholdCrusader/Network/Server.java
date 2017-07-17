package StrongholdCrusader.Network;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.GameObjects.Ship.Ship;
import StrongholdCrusader.Map.MapManager;
import StrongholdCrusader.Map.MapTile;
import StrongholdCrusader.Settings;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
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
        game = new Game(mapId, this);

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

    public static void main(String[] args) {
        Server server = new Server(1);
    }

    private void updateMapObjects() {
        for (GameObject object : game.objects) {
            if (object instanceof Human) {
                Human human = (Human) object;
                Human aroundEnemy = getAroundHumans(human);
//                if(aroundEnemy != null) {
//                    System.out.println(aroundEnemy.type);
//                    human.attack(aroundEnemy);
//                    System.out.println("Health: " + aroundEnemy.health);
//                }
//                else
                human.updatePosition(game.tiles);
            }
            if (object instanceof Ship) {
                ((Ship) object).updatePosition(this, game.tiles);
            }
        }
    }

    private Human getAroundHumans(Human human) {
        LinkedList<Human> aroundEnemyHumans = new LinkedList<>();
        MapTile thisPosition = game.tiles[human.position.x][human.position.y];
        for (int i = 0; i < game.objects.size(); i++) {
            GameObject gameObject = game.objects.get(i);
            if (gameObject instanceof Human && !gameObject.ownerName.equals(human.ownerName)) {
                MapTile otherPosition = game.tiles[gameObject.position.x][gameObject.position.y];
                if (game.getDistanceFromTileToTile(thisPosition, otherPosition) < human.zone) {
                    aroundEnemyHumans.add((Human) gameObject);
                }
            }
        }
        if (aroundEnemyHumans.size() != 0) {
            Human nearestEnemyHuman = aroundEnemyHumans.get(0);
            for (Human aroundEnemyHuman : aroundEnemyHumans) {
                MapTile otherPosition = game.tiles[aroundEnemyHuman.position.x][aroundEnemyHuman.position.y];
                MapTile nearestPosition = game.tiles[nearestEnemyHuman.position.x][nearestEnemyHuman.position.y];
                if (game.getDistanceFromTileToTile(thisPosition, otherPosition) < game.getDistanceFromTileToTile(thisPosition, nearestPosition)) {
                    nearestEnemyHuman = aroundEnemyHuman;
                }
            }
            return nearestEnemyHuman;
        }
        return null;
    }

    @Override
    public void run() {
        try {
            //buffer to receive incoming data
            byte[] buffer = new byte[Settings.PACKET_MAX_SIZE];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
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
                    palace.ownerName = getSenderPlayerByAddress(address).playerName;
                    game.addBuildingToMap(palace);
                    createInitialVassels(palace);

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
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.playerHasVassal(getSenderPlayerByAddress(address))) {
                    if (game.buildingCanCreate(woodCutter, playerPalace))
                        if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.WOOD_CUTTER_CREATION_NEEDED_WOOD)) {
                            assignVassalToBuilding(game.getPlayerRandomVassalId(getSenderPlayerByAddress(address)), woodCutter);
                            game.addBuildingToMap(woodCutter);
                        } else
                            sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                    else
                        sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                } else
                    sendShowAlertRequest("کارگر کافی وجود ندارد!", address, port);
                break;
            }
            case GameEvent.BARRACKS_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Barracks barracks = new Barracks();
                barracks.position = new Pair(x, y);
                barracks.id = generateNewID();
                barracks.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.buildingCanCreate(barracks, playerPalace))
                    if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.BARRACKS_CREATION_NEEDED_WOOD))
                        game.addBuildingToMap(barracks);
                    else
                        sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                else
                    sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                break;
            }
            case GameEvent.FARM_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Farm farm = new Farm();
                farm.position = new Pair(x, y);
                farm.id = generateNewID();
                farm.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.playerHasVassal(getSenderPlayerByAddress(address))) {
                    if (game.buildingCanCreate(farm, playerPalace))
                        if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.FARM_CREATION_NEEDED_WOOD)) {
                            assignVassalToBuilding(game.getPlayerRandomVassalId(getSenderPlayerByAddress(address)), farm);
                            game.addBuildingToMap(farm);
                        } else
                            sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                    else
                        sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                } else
                    sendShowAlertRequest("کارگر کافی وجود ندارد!", address, port);
                break;
            }
            case GameEvent.MARKET_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Market market = new Market();
                market.position = new Pair(x, y);
                market.id = generateNewID();
                market.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (!game.playerHasMarket(getSenderPlayerByAddress(address))) {
                    if (game.buildingCanCreate(market, playerPalace))
                        if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.MARKET_CREATION_NEEDED_WOOD))
                            game.addBuildingToMap(market);
                        else
                            sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                    else
                        sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                } else
                    sendShowAlertRequest("بازار قبلا ساخته شده است!", address, port);
                break;
            }
            case GameEvent.QUARRAY_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Quarry quarry = new Quarry();
                quarry.position = new Pair(x, y);
                quarry.id = generateNewID();
                quarry.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.playerHasVassal(getSenderPlayerByAddress(address))) {
                    if (game.quarryCanCreate(quarry, playerPalace))
                        if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.QUARRY_CREATION_NEEDED_WOOD)) {
                            assignVassalToBuilding(game.getPlayerRandomVassalId(getSenderPlayerByAddress(address)), quarry);
                            game.addBuildingToMap(quarry);
                        } else
                            sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                    else
                        sendShowAlertRequest("معدن باید در محل ذخایر سنگ ساخته شود!", address, port);
                } else
                    sendShowAlertRequest("کارگر کافی وجود ندارد!", address, port);
                break;
            }
            case GameEvent.PORT_CREATED: {
                int x = Integer.parseInt(gameEvent.message.substring(0, gameEvent.message.indexOf(":")));
                int y = Integer.parseInt(gameEvent.message.substring(gameEvent.message.indexOf(":") + 1));
                Port portBuilding = new Port();
                portBuilding.position = new Pair(x, y);
                portBuilding.id = generateNewID();
                portBuilding.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.portCanCreate(portBuilding, playerPalace))
                    if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.PORT_CREATION_NEEDED_WOOD))
                        game.addBuildingToMap(portBuilding);
                    else
                        sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                else
                    sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                break;
            }
            case GameEvent.WORKER_CREATED: {
                int buildingId = Integer.parseInt(gameEvent.message);
                MapTile target = game.getAnEmptyTileAroundBuilding((Building) game.getGameObjectById(buildingId));
                if (target != null) {
                    Worker worker = new Worker();
                    worker.position = new Pair(target.position.x, target.position.y);
                    worker.id = generateNewID();
                    worker.ownerName = getSenderPlayerByAddress(address).playerName;
                    if (game.humanCanCreate(worker))
                        game.addHumanToMap(worker);
                    else
                        sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                } else
                    sendShowAlertRequest("فضای کافی در اطراف قصر موجود نیست!", address, port);
                break;
            }
            case GameEvent.VASSAL_CREATED: {
                if (game.changeResources(getSenderPlayerByAddress(address), "gold", -1 * Settings.CREATE_VASSAL_NEEDED_GOLD)) {
                    if (game.changeResources(getSenderPlayerByAddress(address), "food", -1 * Settings.CREATE_VASSAL_NEEDED_FOOD)) {
                        int buildingId = Integer.parseInt(gameEvent.message);
                        MapTile target = game.getAnEmptyTileAroundBuilding((Building) game.getGameObjectById(buildingId));
                        if (target != null) {
                            Vassal vassal = new Vassal();
                            vassal.position = new Pair(target.position.x, target.position.y);
                            vassal.id = generateNewID();
                            vassal.ownerName = getSenderPlayerByAddress(address).playerName;
                            if (game.humanCanCreate(vassal))
                                game.addHumanToMap(vassal);
                            else
                                sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                        } else
                            sendShowAlertRequest("فضای کافی در اطراف قصر موجود نیست!", address, port);
                    } else {
                        game.changeResources(getSenderPlayerByAddress(address), "food", Settings.CREATE_VASSAL_NEEDED_FOOD);
                        sendShowAlertRequest("غذا به مقدار کافی موجود نیست!", address, port);
                    }
                } else
                    sendShowAlertRequest("طلا کافی نیست!", address, port);
                break;
            }
            case GameEvent.SOLDIER_CREATED: {
                int buildingId = Integer.parseInt(gameEvent.message);
                MapTile target = game.getAnEmptyTileAroundBuilding((Building) game.getGameObjectById(buildingId));
                if (target != null) {
                    Soldier soldier = new Soldier();
                    soldier.position = new Pair(target.position.x, target.position.y);
                    soldier.id = generateNewID();
                    soldier.ownerName = getSenderPlayerByAddress(address).playerName;
                    if (game.humanCanCreate(soldier))
                        if (game.changeResources(getSenderPlayerByAddress(address), "gold", -1 * Settings.SOLDIER_CREATION_NEEDED_GOLD)) {
                            game.addHumanToMap(soldier);
                            sendPlaySoundRequest("SoldierAdd", address, port);
                        } else
                            sendShowAlertRequest("طلا کافی نیست!", address, port);
                    else
                        sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                } else
                    sendShowAlertRequest("فضای کافی در اطراف سربازخانه موجود نیست!", address, port);
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
                sendPacketForAll(createGameEvent.getJSON());
                break;
            }
            case GameEvent.DESTROY_SHIP: {
                int id = Integer.parseInt(gameEvent.message);
                game.removeShip((Ship) game.getGameObjectById(id));
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
            case GameEvent.BUY_RESOURCE: {
                if (gameEvent.message.equals("wood")) {
                    if (game.changeResources(getSenderPlayerByAddress(address), "gold", -5))
                        game.changeResources(getSenderPlayerByAddress(address), "wood", 5);
                }
                if (gameEvent.message.equals("food")) {
                    if (game.changeResources(getSenderPlayerByAddress(address), "gold", -10))
                        game.changeResources(getSenderPlayerByAddress(address), "food", 5);
                }
                break;
            }
            case GameEvent.SELL_RESOURCE: {
                if (gameEvent.message.equals("wood")) {
                    if (game.changeResources(getSenderPlayerByAddress(address), "gold", 3))
                        game.changeResources(getSenderPlayerByAddress(address), "wood", -5);
                }
                if (gameEvent.message.equals("food")) {
                    if (game.changeResources(getSenderPlayerByAddress(address), "gold", 7))
                        game.changeResources(getSenderPlayerByAddress(address), "food", -5);
                }
                break;
            }
            case GameEvent.ATTACK: {
                System.out.println("Attack");
                String[] args = gameEvent.message.split(":");
                int humanId = Integer.parseInt(args[0]);
                int objectId = Integer.parseInt(args[1]);
                attackToObject(humanId, objectId);
                break;
            }
            case GameEvent.SHIP_CREATED: {
                String[] args = gameEvent.message.split(":");
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int portId = Integer.parseInt(args[2]);
                Ship ship = new Ship(new Pair(x, y));
                ship.position = new Pair(x, y);
                ship.id = generateNewID();
                ship.ownerName = getSenderPlayerByAddress(address).playerName;
                Palace playerPalace = (Palace) game.getGameObjectById(getPalaceIdByPlayerName(getSenderPlayerByAddress(address).playerName));
                if (game.shipCanCreate(ship, playerPalace))
                    if (game.changeResources(getSenderPlayerByAddress(address), "wood", -1 * Settings.SHIP_CREATION_NEEDED_WOOD)) {
                        game.addShipToMap(ship);
                        ship.goToTile(game.tiles, game.getRandomEmptySeaTile());
                    } else
                        sendShowAlertRequest("چوب مورد نیاز است!", address, port);
                else
                    sendShowAlertRequest("اینجا قرار نمی گیرد!", address, port);
                break;
            }
        }
    }

    private void sendPlaySoundRequest(String soldierAdd, InetAddress address, int port) {
        GameEvent playSoundGameEvent = new GameEvent(GameEvent.PLAY_SOUND, soldierAdd);
        sendPacket(playSoundGameEvent.getJSON(), address, port);
    }

    private void changeHumanClimb(int humanId, boolean status) {
        Human human = (Human) game.getGameObjectById(humanId);
        ServerPlayer serverPlayer = getPlayerByName(human.ownerName);
        if (status) {
            human.canClimb = true;
            human.speed = human.speed / 2;
            game.changeResources(serverPlayer, "gold", Settings.CHANGE_CLIMB_COST);
        } else {
            human.canClimb = false;
            human.speed = human.speed * 2;
        }
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

    private void attackToObject(int humanId, int objectId) {
        Human human = (Human) game.getGameObjectById(humanId);
        GameObject object = game.getGameObjectById(objectId);
        MapTile target = game.getAnEmptyTileAroundObjects(object);
        if (human != null && object != null && target != null) {
            human.goToTile(game.tiles, target);
            human.attack(object);
        }
    }

    private ServerPlayer getSenderPlayerByAddress(InetAddress address) {
        for (ServerPlayer player : game.players) {
            if (player.address.getHostAddress().equals(address.getHostAddress()))
                return player;
        }
        return null;
    }

    private int getObjectIdByPosition(int x, int y) {
        for (GameObject object : game.objects) {
            if (object instanceof Human) {
                if (object.position.x == x && object.position.y == y) {
                    return object.id;
                }
            } else if (object instanceof Building) {
                if (x >= object.position.x && x <= object.position.x + ((Building) object).size.x && y >= object.position.y && x <= object.position.y + ((Building) object).size.y) {
                    return object.id;
                }
            }
        }
        return -1;
    }

    public boolean sendPacket(String body, InetAddress address, int port) {
        DatagramPacket dp = new DatagramPacket(body.getBytes(), body.getBytes().length, address, port);
        try {
            socket.send(dp);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendPacketForAll(String body) {
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

    private void sendShowAlertRequest(String message, InetAddress address, int port) {
        GameEvent showAlertGameEvent = new GameEvent(GameEvent.SHOW_ALERT, message);
        sendPacket(showAlertGameEvent.getJSON(), address, port);
    }

    private void createInitialVassels(Palace palace) {
        for (int i = 0; i < 10; i++) {
            Vassal vassal = new Vassal();
            vassal.ownerName = palace.ownerName;
            vassal.id = generateNewID();
            vassal.position = new Pair(palace.position.x + (i % 5), palace.position.y + palace.size.y + (i / 5) + 1);
            game.addHumanToMap(vassal);
        }
    }

    private void assignVassalToBuilding(int vassalId, Building building) {
        Vassal vassal = (Vassal) game.getGameObjectById(vassalId);
        Worker worker = new Worker();
        worker.position = vassal.position;
        worker.ownerName = vassal.ownerName;
        worker.health = vassal.health;
        worker.id = generateNewID();
        if (building instanceof Quarry)
            worker.canClimb = true;
        MapTile target = game.getAnEmptyTileAroundBuilding(building);
        if (target != null)
            worker.goToTile(game.tiles, target);
        game.removeHuman(vassal);
        GameEvent createGameEvent = new GameEvent(GameEvent.DISTROY_BUILDING, String.valueOf(vassal.id));
        sendPacketForAll(createGameEvent.getJSON());
        game.addHumanToMap(worker);
    }

    public void emptyShipAndSendThatAgain(int id) {
        Ship ship = (Ship) game.getGameObjectById(id);
        ship.collectedFood = 0;
        ServerPlayer serverPlayer = getPlayerByName(ship.ownerName);
        game.changeResources(serverPlayer, "food", Settings.SHIP_MAX_CAPACITY);
        ship.goToTile(game.tiles, game.getRandomEmptySeaTile());
        ship.mode = Ship.shipMode.GOING_TO_TARGET;
    }

    private ServerPlayer getPlayerByName(String username) {
        for (ServerPlayer player : game.players) {
            if (player.playerName.equals(username))
                return player;
        }
        return null;
    }
}
