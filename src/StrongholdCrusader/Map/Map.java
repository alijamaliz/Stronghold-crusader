package StrongholdCrusader.Map;import StrongholdCrusader.ClientPlayer;import StrongholdCrusader.GameObjects.GameObject;import StrongholdCrusader.GameObjects.NatureObject;import java.io.Serializable;import java.util.LinkedList;/** * Created by Baran on 5/29/2017. */public class Map implements Serializable {    public MapTile[][] tiles;    public LinkedList<GameObject> objects;    LinkedList<NatureObject> natureObjects;    private MapGUI gui;    private ClientPlayer clientPlayer;    public Map() {        objects = new LinkedList<>();    }    public Map(ClientPlayer clientPlayer) {        objects = new LinkedList<>();        gui = new MapGUI(this, clientPlayer.username);        this.clientPlayer = clientPlayer;    }    public MapGUI getGui() {        return gui;    }    public void setMapId(int id) {        tiles = MapManager.getMapTiles(id);        natureObjects = MapManager.getNatureObjects(id);    }    public void showMapScreen() {        gui.showMap();        new Thread(gui).start();    }    public void sendGameEvent(int type, String message) {        if (clientPlayer.isPlayerAlive)            clientPlayer.client.sendGameEvent(type, message);    }    public GameObject findGameObjectObjectById(int id) {        for (GameObject object : objects) {            if (object.id == id)                return object;        }        return null;    }    public void showMessage(String message) {        gui.showMessage(message);    }}