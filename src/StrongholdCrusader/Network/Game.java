package StrongholdCrusader.Network;import StrongholdCrusader.GameObjects.Buildings.*;import StrongholdCrusader.GameObjects.GameObject;import StrongholdCrusader.GameObjects.Humans.Human;import StrongholdCrusader.GameObjects.Humans.Soldier;import StrongholdCrusader.GameObjects.Humans.Vassal;import StrongholdCrusader.GameObjects.Humans.Worker;import StrongholdCrusader.GameObjects.Pair;import StrongholdCrusader.Map.*;import StrongholdCrusader.Settings;import javafx.application.Application;import java.util.LinkedList;import java.util.Random;/** * Created by Baran on 5/29/2017. */public class Game {    public LinkedList<ServerPlayer> players;    public LinkedList<GameObject> objects;    public MapTile[][] tiles;    public int mapId;    Server server;    private LinkedList<Pair> palacePositions;    public Game(int mapId, Server server) {        players = new LinkedList<>();        objects = new LinkedList<>();        tiles = MapManager.getMapTiles(mapId);        this.mapId = mapId;        palacePositions = MapManager.getPalacePositions(mapId);        this.server = server;        //Game Thread        new Thread(new Runnable() {            @Override            public void run() {                while (true)                    try {                        for (ServerPlayer player : players) {                            for (GameObject gameObject : getGameObjectByPlayerName(player.playerName)) {                                if (gameObject.health <= 0) {                                    if (gameObject instanceof Building) {                                        if (gameObject instanceof Palace) {                                            losePlayer(player);                                        } else                                            removeBuilding((Building) gameObject);                                    }                                    if (gameObject instanceof Human)                                        removeHuman((Human) gameObject);                                }                                if (gameObject instanceof Vassal || gameObject instanceof Worker) {                                    changeResources(player, "food", -1);                                }                                if (gameObject instanceof Soldier) {                                    changeResources(player, "food", -2);                                }                                if (gameObject instanceof Farm) {                                    changeResources(player, "food", 5);                                }                                if (gameObject instanceof WoodCutter) {                                    changeResources(player, "wood", 5);                                }                            }                        }                        Thread.sleep(1000);                    } catch (InterruptedException e) {                        e.printStackTrace();                    }            }        }).start();    }    private void losePlayer(ServerPlayer player) {        //Alert players        GameEvent gameOverGameEvent = new GameEvent(GameEvent.PLAYER_LOSE, player.playerName);        server.sendPacketForAll(gameOverGameEvent.getJSON());        LinkedList<GameObject> toRemoveObjects = new LinkedList<>();        //Remove all player objects        for (int i = 0; i < objects.size(); i++) {            GameObject object = objects.get(i);            if (object.ownerName.equals(player.playerName))                toRemoveObjects.add(object);        }        for (GameObject toRemoveObject : toRemoveObjects) {            if (toRemoveObject instanceof Building) {                removeBuilding((Building) toRemoveObject);                GameEvent createGameEvent = new GameEvent(GameEvent.DISTROY_BUILDING, String.valueOf(toRemoveObject.id));                server.sendPacketForAll(createGameEvent.getJSON());            }            if (toRemoveObject instanceof Human) {                removeHuman((Human) toRemoveObject);                GameEvent createGameEvent = new GameEvent(GameEvent.DISTROY_BUILDING, String.valueOf(toRemoveObject.id));                server.sendPacketForAll(createGameEvent.getJSON());            }        }        //Remove  from Players        players.remove(player);        //Check for game ends        if (players.size() == 1) {            endGame(players.peek());        }    }    private void endGame(ServerPlayer winner) {        GameEvent gameOverGameEvent = new GameEvent(GameEvent.YOU_WIN, "");        server.sendPacket(gameOverGameEvent.getJSON(), winner.address, winner.port);        System.out.println("Game Ends!");        System.exit(0);    }    public Pair getRandomPalacePosition() {        Random random = new Random();        int index = random.nextInt(palacePositions.size());        Pair palacePosition = palacePositions.get(index);        palacePositions.remove(index);        return palacePosition;    }    public void addHumanToMap(Human human) {        objects.add(human);        tiles[human.position.x][human.position.y].filled = true;    }    public boolean humanCanCreate(Human human) {        if (human.position.x > Settings.MAP_WIDTH_RESOLUTION || human.position.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        if (!(tiles[human.position.x][human.position.y] instanceof Plain))            return false;        return !tiles[human.position.x][human.position.y].filled;    }    public void addBuildingToMap(Building building) {        objects.add(building);        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                tiles[i][j].filled = true;            }        }    }    public void removeBuilding(Building building) {        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                tiles[i][j].filled = false;            }        }        objects.remove(building);    }    public void removeHuman(Human human) {        tiles[human.position.x][human.position.y].filled = false;        objects.remove(human);    }    public boolean buildingCanCreate(Building building, Palace playerPalace) {        //Check city radius        if (getDistanceFromTileToTile(tiles[playerPalace.position.x][playerPalace.position.y], tiles[building.position.x][building.position.y]) > Settings.PLAYER_CITY_RADIUS)            return false;        //Check out of screen        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        //Check empty tiles        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                if (tiles[i][j].filled || !(tiles[i][j] instanceof Plain))                    return false;            }        }        return true;    }    public boolean portCanCreate(Port port, Palace playerPalace) {        //Check city radius        if (getDistanceFromTileToTile(tiles[playerPalace.position.x][playerPalace.position.y], tiles[port.position.x][port.position.y]) > Settings.PLAYER_CITY_RADIUS)            return false;        int seaTiles = 0;        if (port.position.x + port.size.x > Settings.MAP_WIDTH_RESOLUTION || port.position.y + port.size.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        for (int i = port.position.x; i < port.position.x + port.size.x; i++) {            for (int j = port.position.y; j < port.position.y + port.size.y; j++) {                if (tiles[i][j].filled || tiles[i][j] instanceof Mountain)                    return false;                if (tiles[i][j] instanceof Sea)                    seaTiles++;            }        }        if (seaTiles == 0 || seaTiles == 16)            return false;        return true;    }    public boolean quarryCanCreate(Quarry quarry, Palace playerPalace) {        //Check city radius        if (getDistanceFromTileToTile(tiles[playerPalace.position.x][playerPalace.position.y], tiles[quarry.position.x][quarry.position.y]) > Settings.PLAYER_CITY_RADIUS) {            return false;        }        int mountainTiles = 0;        if (quarry.position.x + quarry.size.x > Settings.MAP_WIDTH_RESOLUTION || quarry.position.y + quarry.size.y > Settings.MAP_HEIGHT_RESOLUTION) {            return false;        }        for (int i = quarry.position.x; i < quarry.position.x + quarry.size.x; i++) {            for (int j = quarry.position.y; j < quarry.position.y + quarry.size.y; j++) {                if (tiles[i][j].filled || tiles[i][j] instanceof Sea) {                    return false;                }                if (tiles[i][j] instanceof Mountain)                    mountainTiles++;            }        }        if (mountainTiles < 2)            return false;        return true;    }    public GameObject getGameObjectById(int id) {        for (GameObject object : objects) {            if (object.id == id)                return object;        }        return null;    }    public LinkedList<GameObject> getGameObjectByPlayerName(String playerName) {        LinkedList<GameObject> playerObjects = new LinkedList<>();        for (GameObject object : objects) {            if (object.ownerName.equals(playerName))                playerObjects.add(object);        }        return playerObjects;    }    public boolean changeResources(ServerPlayer player, String resourceName, int amount) {        switch (resourceName) {            case "gold": {                if (player.golds + amount >= 0) {                    player.golds += amount;                    return true;                }                return false;            }            case "wood": {                if (player.woods + amount >= 0) {                    player.woods += amount;                    return true;                }                return false;            }            case "food": {                if (player.foods + amount >= 0) {                    player.foods += amount;                    return true;                }                return false;            }        }        return false;    }    public void changeHealth(Human human , GameObject object) {        if(object.health - human.power <0)        {            object.health=0;        }        else        {            object.health-=human.power;        }    }    public boolean playerHasVassal(ServerPlayer player) {        for (GameObject object : objects) {            if (object instanceof Vassal && object.ownerName.equals(player.playerName)) {                return true;            }        }        return false;    }    public int getPlayerRandomVassalId(ServerPlayer player) {        LinkedList<Vassal> vassals = new LinkedList<>();        for (GameObject object : objects) {            if (object instanceof Vassal && object.ownerName.equals(player.playerName)) {                vassals.add((Vassal) object);            }        }        Vassal selected = vassals.get(new Random().nextInt(vassals.size()));        return selected.id;    }    public double getDistanceFromTileToTile(MapTile start, MapTile destination) {        int distanceX = Math.abs(start.position.x - destination.position.x);        int distanceY = Math.abs(start.position.y - destination.position.y);        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));    }    public boolean playerHasMarket(ServerPlayer player) {        for (GameObject gameObject : getGameObjectByPlayerName(player.playerName)) {            if (gameObject instanceof Market)                return true;        }        return false;    }    public MapTile getAnEmptyTileAroundBuilding(Building building) {        for (int i = building.position.x - 1; i < building.position.x + building.size.x + 1; i++) {            for (int j = building.position.y - 1; j < building.position.y + building.size.y + 1; j++) {                if (!tiles[i][j].filled && !(tiles[i][j] instanceof Sea))                    return tiles[i][j];            }        }        return null;    }    public MapTile getAnEmptyTileAroundObjects(GameObject object) {        if(object instanceof Building) {            for (int i = object.position.x - 1; i < object.position.x + ((Building)object).size.x + 1; i++) {                for (int j = object.position.y - 1; j < object.position.y + ((Building)object).size.y + 1; j++) {                    if (!tiles[i][j].filled && !(tiles[i][j] instanceof Sea)){                        return tiles[i][j];                    }                }            }        }        else if(object instanceof Human)        {            for (int i = object.position.x - 1; i < object.position.x + 2; i++) {                for (int j = object.position.y - 1; j < object.position.y + 2; j++) {                    if (!tiles[i][j].filled && !(tiles[i][j] instanceof Sea))                        return tiles[i][j];                }            }        }        return null;    }}