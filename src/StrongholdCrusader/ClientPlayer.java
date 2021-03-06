package StrongholdCrusader;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.GameObjects.Ship.Ship;
import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Network.Client;
import StrongholdCrusader.Network.GameEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Baran on 6/3/2017.
 */
public class ClientPlayer {
    public String username;
    public Client client;
    public boolean isPlayerAlive = true;
    private Map map;
    private MenuGUI menuGUI;

    ClientPlayer(String username, String serverIP, MenuGUI menuGUI) {
        this.username = username;
        client = new Client(serverIP);
        client.sendJoinRequest(username);
        map = new Map(this);
        this.menuGUI = menuGUI;

        Runnable gameEventHandleRunnable = () -> {
            while (true) {
                if (client.hasNewEvent()) {
                    analyseGameEvent(client.getEvent());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        };
        new Thread(gameEventHandleRunnable).start();
    }

    private void analyseGameEvent(GameEvent gameEvent) {
        switch (gameEvent.type) {
            case GameEvent.USER_JOINED_TO_NETWORK: {
                String username = gameEvent.message.substring(0, gameEvent.message.indexOf(','));
                String address = gameEvent.message.substring(gameEvent.message.indexOf(",") + 1);
                menuGUI.addPlayerToTable(username + ":" + address);
                break;
            }
            case GameEvent.START_GAME: {
                map.setMapId(Integer.parseInt(gameEvent.message));
                map.showMapScreen();
                break;
            }
            case GameEvent.SHOW_ALERT: {
                switch (gameEvent.message) {
                    case "اینجا قرار نمی گیرد!": {
                        map.getGui().playSound("CantBuild");
                        break;
                    }
                    case "چوب مورد نیاز است!": {
                        map.getGui().playSound("WoodNeed");
                        break;
                    }
                    case "طلا کافی نیست!": {
                        map.getGui().playSound("GoldNeed");
                        break;
                    }
                    case "معدن باید در محل ذخایر آهن ذخیره شود!": {
                        map.getGui().playSound("CantQuarry");
                        break;
                    }
                }
                showMessage(gameEvent.message);
                break;
            }
            case GameEvent.PLAY_SOUND: {
                map.getGui().playSound(gameEvent.message);
                break;
            }
            case GameEvent.DISTROY_BUILDING: {
                int id = Integer.parseInt(gameEvent.message);
                map.objects.remove(getObjectsById(id));
                break;
            }
            case GameEvent.MAP_OBJECTS: {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject objects = (JSONObject) jsonParser.parse(gameEvent.message);
                    JSONArray objectsArray = (JSONArray) objects.get("objects");
                    for (Object o : objectsArray) {
                        JSONObject obj = (JSONObject) o;
                        int x = ((Long) (((JSONObject) obj.get("position")).get("x"))).intValue();
                        int y = ((Long) (((JSONObject) obj.get("position")).get("y"))).intValue();
                        int id = ((Long) (obj.get("id"))).intValue();
                        int health = ((Long) (obj.get("health"))).intValue();
                        String owner = (String) obj.get("ownerName");
                        if (map.findGameObjectObjectById(id) == null) { //Create object
                            if (obj.get("type").equals("Palace")) {
                                Palace palace = new Palace(map.getGui());
                                palace.position = new Pair(x, y);
                                palace.id = id;
                                palace.health = health;
                                palace.ownerName = owner;
                                map.objects.add(palace);
                            }
                            if (obj.get("type").equals("WoodCutter")) {
                                WoodCutter woodCutter = new WoodCutter(map.getGui());
                                woodCutter.position = new Pair(x, y);
                                woodCutter.id = id;
                                woodCutter.health = health;
                                woodCutter.ownerName = owner;
                                map.objects.add(woodCutter);
                            }
                            if (obj.get("type").equals("Quarry")) {
                                Quarry quarry = new Quarry(map.getGui());
                                quarry.position = new Pair(x, y);
                                quarry.id = id;
                                quarry.health = health;
                                quarry.ownerName = owner;
                                map.objects.add(quarry);
                            }
                            if (obj.get("type").equals("Market")) {
                                Market market = new Market(map.getGui());
                                market.position = new Pair(x, y);
                                market.id = id;
                                market.health = health;
                                market.ownerName = owner;
                                map.objects.add(market);
                            }
                            if (obj.get("type").equals("Port")) {
                                Port port = new Port(map.getGui());
                                port.position = new Pair(x, y);
                                port.id = id;
                                port.health = health;
                                port.ownerName = owner;
                                map.objects.add(port);
                            }
                            if (obj.get("type").equals("Farm")) {
                                Farm farm = new Farm(map.getGui());
                                farm.position = new Pair(x, y);
                                farm.id = id;
                                farm.health = health;
                                farm.ownerName = owner;
                                map.objects.add(farm);
                            }
                            if (obj.get("type").equals("Barracks")) {
                                Barracks barracks = new Barracks(map.getGui());
                                barracks.position = new Pair(x, y);
                                barracks.id = id;
                                barracks.health = health;
                                barracks.ownerName = owner;
                                map.objects.add(barracks);
                            }
                            if (obj.get("type").equals("Worker")) {
                                Worker worker = new Worker(map.getGui());
                                worker.position = new Pair(x, y);
                                worker.id = id;
                                worker.health = health;
                                worker.ownerName = owner;
                                worker.canClimb = (boolean) obj.get("canClimb");
                                map.objects.add(worker);
                            }
                            if (obj.get("type").equals("Vassal")) {
                                Vassal vassal = new Vassal(map.getGui());
                                vassal.position = new Pair(x, y);
                                vassal.id = id;
                                vassal.health = health;
                                vassal.ownerName = owner;
                                vassal.canClimb = (boolean) obj.get("canClimb");
                                map.objects.add(vassal);
                            }
                            if (obj.get("type").equals("Soldier")) {
                                Soldier soldier = new Soldier(map.getGui());
                                soldier.position = new Pair(x, y);
                                soldier.id = id;
                                soldier.health = health;
                                soldier.ownerName = owner;
                                soldier.canClimb = (boolean) obj.get("canClimb");
                                System.out.println(soldier.canClimb);
                                map.objects.add(soldier);
                            }
                            if (obj.get("type").equals("Ship")) {
                                Ship ship = new Ship(map.getGui());
                                ship.position = new Pair(x, y);
                                ship.id = id;
                                ship.health = health;
                                ship.ownerName = owner;
                                map.objects.add(ship);
                            }
                        } else { //Update object
                            GameObject gameObject = map.findGameObjectObjectById(id);
                            gameObject.position = new Pair(x, y);
                            gameObject.health = health;
                            if (gameObject instanceof Human)
                                ((Human) gameObject).canClimb = (boolean) obj.get("canClimb");
                        }
                    }
                } catch (ParseException e) {
                    //e.printStackTrace();
                }
                break;
            }
            case GameEvent.FOCUS_ON_BUILDING: {
                String[] args = gameEvent.message.split(":");
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                map.getGui().focusOnTile(map.tiles[x][y]);
                break;
            }
            case GameEvent.RESOURCES: {
                String[] args = gameEvent.message.split(":");
                int golds = Integer.parseInt(args[0]);
                int foods = Integer.parseInt(args[1]);
                int woods = Integer.parseInt(args[2]);
                map.getGui().updateRefrences(golds, foods, woods);
                break;
            }
            case GameEvent.PLAYER_LOSE: {
                if (gameEvent.message.equals(username)) {
                    map.getGui().showMessage("You lose!");
                    isPlayerAlive = false;
                    map.getGui().gameOver(false);
                } else {
                    map.getGui().showMessage(gameEvent.message + " نابود شد!");
                }
                break;
            }
            case GameEvent.YOU_WIN: {
                map.getGui().showMessage("You Win!");
                isPlayerAlive = false;
                map.getGui().gameOver(true);
                break;
            }
        }
    }

    private void showMessage(String message) {
        map.showMessage(message);
    }

    private GameObject getObjectsById(int id) {
        for (GameObject gameObject : map.objects) {
            if (gameObject.id == id) {
                return gameObject;
            }
        }
        return null;
    }

}

