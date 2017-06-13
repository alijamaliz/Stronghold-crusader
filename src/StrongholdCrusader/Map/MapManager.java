package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
import StrongholdCrusader.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class MapManager implements Serializable {
    //Get default maps by id
    //0 is Plain, 1 is Mountain, 2 is Sea
    public MapTile[][] getMapById(int id) {
        MapTile[][] tiles = new MapTile[Settings.MAP_WIDTH_RESOLUTION][Settings.MAP_HEIGHT_RESOLUTION];
        String filename = "Resources/maps/map" + id + ".map";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
            for (int i = 0; i < Settings.MAP_WIDTH_RESOLUTION; i++) {
                String line = bufferedReader.readLine();
                for (int j = 0; j < Settings.MAP_HEIGHT_RESOLUTION; j++) {
                    if (line.charAt(j) == '0')
                        tiles[i][j] = new Plain();
                    if (line.charAt(j) == '1')
                        tiles[i][j] = new Mountain();
                    if (line.charAt(0) == '2')
                        tiles[i][j] = new Sea();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    public void saveMap(Map map, int mapID) ///Saving Map With ObjectStreams into files
    {
        try {
            File mapFile = new File("../../Resources/maps/map" + mapID + ".map");
            FileOutputStream fileOutputStream = new FileOutputStream(mapFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map LoadMap(int mapID) /// Loading Map Files which were saved with ObjectStreams
    {
        Map map = null;
        try {
            File mapFile = new File("../../Resources/maps/map" + mapID + ".map");
            FileInputStream fileInputStream = new FileInputStream(mapFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public JSONObject mapTilesToJSON(Map map) ///Gives MapTile Array and Environment of map in JSON type
    {
        JSONObject mapTileJSON = new JSONObject();
        JSONArray tiles = new JSONArray();
        JSONArray oneTile = new JSONArray();
        JSONObject pos = new JSONObject();
        for (int i = 0; i < Settings.MAP_WIDTH_RESOLUTION; i++) {
            for (int j = 0; j < Settings.MAP_HEIGHT_RESOLUTION; j++) {
                JSONObject tile = (JSONObject) oneTile.clone();
                JSONObject position = (JSONObject) pos.clone();
                position.put("x", new Integer(i));
                position.put("y", new Integer(j));
                tile.put("position", position);
                tile.put("type", map.tiles[i][j].type);
                tile.put("environment", map.tiles[i][j].environment);
                tiles.add(tile);
            }
        }
        mapTileJSON.put("tiles", tiles);
        return mapTileJSON;
    }

    public static JSONObject mapObjectsToJSON(LinkedList<GameObject> mapObjects) ///Gives objects of map in JSON type
    {
        JSONObject mapObjectsJSON = new JSONObject();
        JSONArray objects = new JSONArray();
        for (GameObject gameObject : mapObjects) {
            JSONObject object = new JSONObject();
            JSONObject position = new JSONObject();
            ///adding position to object
            position.put("x", new Integer(gameObject.position.x));
            position.put("y", new Integer(gameObject.position.y));
            object.put("position", position);
            ///adding ownerName to object
            object.put("ownerName", gameObject.ownerName);
            ///adding health
            object.put("health", new Integer(gameObject.health));
            ///adding object type
            object.put("type", gameObject.type);
            objects.add(object);
        }
        mapObjectsJSON.put("objects", objects);
        return mapObjectsJSON;
    }

    /*public Map JSONtilesToMap(JSONObject mapTiles) {
        Map map = new Map();
        Integer i;
        Integer j;
        Integer environment;
        JSONObject pos;
        map.tiles = new MapTile[Settings.MAP_WIDTH_RESOLUTION][Settings.MAP_HEIGHT_RESOLUTION];
        ///GEtting info from JSON
        JSONArray tiles = (JSONArray) mapTiles.get("tiles");
        for (Object tileObject : tiles) {
            JSONObject tile = (JSONObject) tileObject;
            pos = (JSONObject) tile.get("position");
            i = (Integer) pos.get("x");
            j = (Integer) pos.get("y");
            String type = (String) tile.get("type");
            environment = (Integer) tile.get("environment");
            ///Creating Tile
            switch (type) {
                case "Plain": {
                    map.tiles[i.intValue()][j.intValue()] = new Plain();
                }
                break;
                case "Mountain": {
                    map.tiles[i.intValue()][j.intValue()] = new Mountain();
                }
                break;
                case "Sea": {
                    map.tiles[i.intValue()][j.intValue()] = new Sea();
                }
                break;
            }
            map.tiles[i.intValue()][j.intValue()].environment = environment;
        }
        return map;
    }*/

    public Map JSONobjectsToMap(JSONObject objectArray, Map map) {
        GameObject addingObject = null;
        Integer i;
        Integer j;
        String ownerName;
        Integer health;
        String name;
        String type;
        JSONObject pos;
        JSONArray objects = (JSONArray) objectArray.get("objects");
        for (Object object : objects) {
            ///Getting info from JSON
            JSONObject oneObject = (JSONObject) object;
            pos = (JSONObject) oneObject.get("position");
            i = (Integer) pos.get("x");
            j = (Integer) pos.get("y");
            ownerName = (String) oneObject.get("ownerName");
            health = (Integer) oneObject.get("health");
            name = (String) oneObject.get("name");
            type = (String) oneObject.get("type");
            ///Creating Object
            switch (type) {
                case "Barracks": {
                    addingObject = new Barracks();
                }
                break;
                case "Farm": {
                    addingObject = new Farm();
                }
                break;
                case "Market": {
                    addingObject = new Market();
                }
                break;
                case "Palace": {
                    addingObject = new Palace();
                }
                break;
                case "Port": {
                    addingObject = new Port();
                }
                break;
                case "Quarry": {
                    addingObject = new Quarry();
                }
                break;
                case "WoodCutter": {
                    addingObject = new WoodCutter();
                }
                break;
                case "Soldier": {
                    addingObject = new Soldier();
                }
                break;
                case "Vassal": {
                    addingObject = new Vassal();
                }
                break;
                case "Worker": {
                    addingObject = new Worker();
                }
                break;
            }
            ///Adding info to object
            if (addingObject != null) {
                addingObject.health = health.intValue();
                addingObject.position.x = i.intValue();
                addingObject.position.y = j.intValue();
                addingObject.ownerName = ownerName;
            }
            ///Adding to Map
            if (!map.objects.contains(addingObject)) {
                map.objects.add(addingObject);
            }
        }
        return map;
    }
}
