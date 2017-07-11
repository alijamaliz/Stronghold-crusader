package StrongholdCrusader.Network;import StrongholdCrusader.GameObjects.Buildings.*;import StrongholdCrusader.GameObjects.GameObject;import StrongholdCrusader.GameObjects.Humans.Human;import StrongholdCrusader.GameObjects.Humans.Soldier;import StrongholdCrusader.GameObjects.Humans.Vassal;import StrongholdCrusader.GameObjects.Humans.Worker;import StrongholdCrusader.GameObjects.Pair;import StrongholdCrusader.Map.*;import StrongholdCrusader.Settings;import java.util.ArrayList;import java.util.LinkedList;import java.util.Random;/** * Created by Baran on 5/29/2017. *//*public class Game {    public LinkedList<ServerPlayer> players;    public LinkedList<GameObject> objects;    public MapTile[][] tiles;    public int mapId;    private ArrayList<Pair> palacePositions;    public Game(int mapId) {        players = new LinkedList<>();        objects = new LinkedList<>();        tiles = MapManager.getMapTilesById(mapId);        this.mapId = mapId;        palacePositions = MapManager.getMapPalacePositionsById(mapId);        //Game Thread        new Thread(new Runnable() {            @Override            public void run() {                while (true)                    try {                        for (ServerPlayer player : players) {                            for (GameObject gameObject : getGameObjectByPlayerName(player.playerName)) {                                if (gameObject instanceof Vassal || gameObject instanceof Worker) {                                    player.foods -= 1;                                    System.out.println(player.foods);                                }                                if (gameObject instanceof Soldier) {                                    System.out.println("Solier");                                    player.foods -= 2;                                }                            }                        }                        Thread.sleep(1000);                    } catch (InterruptedException e) {                        e.printStackTrace();                    }            }        }).start();    }    public Pair getRandomPalacePosition() {        Random random = new Random();        int index = random.nextInt(palacePositions.size());        Pair palacePosition = palacePositions.get(index);        palacePositions.remove(index);        return palacePosition;    }    public void addHumanToMap(Human human) {        objects.add(human);        tiles[human.position.x][human.position.y].filled = true;    }    public boolean humanCanCreate(Human human) {        if (human.position.x > Settings.MAP_WIDTH_RESOLUTION || human.position.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        return !tiles[human.position.x][human.position.y].filled;    }    public void addBuildingToMap(Building building) {        objects.add(building);        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                tiles[i][j].filled = true;            }        }    }    public void removeBuilding(Building building){        for(int i = building.position.x ; i<building.position.x + building.size.x;i++)        {            for (int j = building.position.y ; j<building.position.y + building.size.y ; j++)            {                tiles[i][j].filled=false;            }        }        objects.remove(building);    }    public boolean buildingCanCreate(Building building) {        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                if (tiles[i][j].filled)                    return false;            }        }        return true;    }    public GameObject getGameObjectById(int id) {        for (GameObject object : objects) {            if (object.id == id)                return object;        }        return null;    }    public LinkedList<GameObject> getGameObjectByPlayerName(String playerName) {        LinkedList<GameObject> playerObjects = new LinkedList<>();        for (GameObject object : objects) {            if (object.ownerName.equals(playerName))                playerObjects.add(object);        }        return playerObjects;    }}*/public class Game {    public LinkedList<ServerPlayer> players;    public LinkedList<GameObject> objects;    public MapTile[][] tiles;    public int mapId;    private LinkedList<Pair> palacePositions;    public Game(int mapId) {        players = new LinkedList<>();        objects = new LinkedList<>();        tiles = MapManager.getMapTiles(mapId);        this.mapId = mapId;        palacePositions = MapManager.getPalacePositions(mapId);        //Game Thread        new Thread(new Runnable() {            @Override            public void run() {                while (true)                    try {                        for (ServerPlayer player : players) {                            for (GameObject gameObject : getGameObjectByPlayerName(player.playerName)) {                                if (gameObject instanceof Vassal || gameObject instanceof Worker) {                                    changeResources(player, "food", -1);                                }                                if (gameObject instanceof Soldier) {                                    changeResources(player, "food", -2);                                }                                if (gameObject instanceof Farm) {                                    changeResources(player, "food", 5);                                }                                if (gameObject instanceof WoodCutter) {                                    changeResources(player, "wood", 5);                                }                            }                        }                        Thread.sleep(1000);                    } catch (InterruptedException e) {                        e.printStackTrace();                    }            }        }).start();    }    public Pair getRandomPalacePosition() {        Random random = new Random();        int index = random.nextInt(palacePositions.size());        Pair palacePosition = palacePositions.get(index);        palacePositions.remove(index);        return palacePosition;    }    public void addHumanToMap(Human human) {        objects.add(human);        tiles[human.position.x][human.position.y].filled = true;    }    public boolean humanCanCreate(Human human) {        if (human.position.x > Settings.MAP_WIDTH_RESOLUTION || human.position.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        if (!(tiles[human.position.x][human.position.y] instanceof Plain))            return false;        return !tiles[human.position.x][human.position.y].filled;    }    public void addBuildingToMap(Building building) {        objects.add(building);        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                tiles[i][j].filled = true;            }        }    }    public void removeBuilding(Building building){        for(int i = building.position.x ; i<building.position.x + building.size.x;i++)        {            for (int j = building.position.y ; j<building.position.y + building.size.y ; j++)            {                tiles[i][j].filled=false;            }        }        objects.remove(building);    }    public void removeHuman(Human human){        tiles[human.position.x][human.position.y].filled=false;        objects.remove(human);    }    public boolean buildingCanCreate(Building building) {        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                if (tiles[i][j].filled || !(tiles[i][j] instanceof Plain))                    return false;            }        }        return true;    }    public boolean portCanCreate(Port building) {        int seaTiles = 0;        if (building.position.x + building.size.x > Settings.MAP_WIDTH_RESOLUTION || building.position.y + building.size.y > Settings.MAP_HEIGHT_RESOLUTION)            return false;        for (int i = building.position.x; i < building.position.x + building.size.x; i++) {            for (int j = building.position.y; j < building.position.y + building.size.y; j++) {                if (tiles[i][j].filled || tiles[i][j] instanceof Mountain)                    return false;                if (tiles[i][j] instanceof Sea)                    seaTiles++;            }        }        if (seaTiles == 0 || seaTiles == 16)            return false;        return true;    }    public GameObject getGameObjectById(int id) {        for (GameObject object : objects) {            if (object.id == id)                return object;        }        return null;    }    public LinkedList<GameObject> getGameObjectByPlayerName(String playerName) {        LinkedList<GameObject> playerObjects = new LinkedList<>();        for (GameObject object : objects) {            if (object.ownerName.equals(playerName))                playerObjects.add(object);        }        return playerObjects;    }    public boolean changeResources(ServerPlayer player, String resourceName, int amount) {        switch (resourceName) {            case "gold": {                if (player.golds + amount >= 0) {                    player.golds += amount;                    return true;                }                return false;            }            case "wood": {                if (player.woods + amount >= 0) {                    player.woods += amount;                    return true;                }                return false;            }            case "food": {                if (player.foods + amount >= 0) {                    player.foods += amount;                    return true;                }                return false;            }        }        return false;    }    public boolean playerHasVassal(ServerPlayer player) {        for (GameObject object : objects) {            if (object instanceof Vassal && object.ownerName.equals(player.playerName))            {                return true;            }        }        return false;    }    public int getPlayerRandomVassalId(ServerPlayer player) {        LinkedList<Vassal> vassals = new LinkedList<>();        for (GameObject object : objects) {            if (object instanceof Vassal && object.ownerName.equals(player.playerName))            {                vassals.add((Vassal) object);            }        }        Vassal selected = vassals.get(new Random().nextInt(vassals.size()));        return selected.id;    }}