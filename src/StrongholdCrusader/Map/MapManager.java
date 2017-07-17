package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Buildings.*;
import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
import StrongholdCrusader.GameObjects.NatureObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class MapManager implements Serializable {
    //Get default maps by id
    //0 is Plain, 1 is Mountain, 2 is Sea
    private static JSONObject getMapJSONObject(int id) {
        String filename = "Resources/maps/map" + id + ".map";
        String mapJSON = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
            String line = "";
            while ((line = bufferedReader.readLine()) != null && line.length() != 0) {
                mapJSON += line;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) jsonParser.parse(mapJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static MapTile[][] getMapTiles(int id) {
        MapTile[][] tiles = new MapTile[Settings.MAP_WIDTH_RESOLUTION][Settings.MAP_HEIGHT_RESOLUTION];

        JSONObject jsonObject = getMapJSONObject(id);
        String mapTiles = (String) jsonObject.get("tiles");

        for (int i = 0; i < Settings.MAP_WIDTH_RESOLUTION; i++) {
            for (int j = 0; j < Settings.MAP_HEIGHT_RESOLUTION; j++) {
                int index = i * Settings.MAP_WIDTH_RESOLUTION + j;
                if (mapTiles.charAt(index) == '0')
                    tiles[j][i] = new Plain(j, i);
                if (mapTiles.charAt(index) == '1')
                    tiles[j][i] = new Mountain(j, i);
                if (mapTiles.charAt(index) == '2')
                    tiles[j][i] = new Sea(j, i);
            }
        }
        return tiles;
    }

    public static LinkedList<Pair> getPalacePositions(int id) {
        LinkedList<Pair> places = new LinkedList<>();
        JSONObject jsonObject = getMapJSONObject(id);
        JSONArray palacePlacesArray = (JSONArray) jsonObject.get("palacePlaces");
        for (Object o : palacePlacesArray) {
            JSONArray palacePlace = (JSONArray) o;
            int x = ((Long) (palacePlace.get(0))).intValue();
            int y = ((Long) (palacePlace.get(1))).intValue();
            places.add(new Pair(x, y));
        }
        return places;
    }

    static LinkedList<NatureObject> getNatureObjects(int id) {
        LinkedList<NatureObject> natureObjects = new LinkedList<>();
        JSONObject jsonObject = getMapJSONObject(id);
        JSONArray natureObjectsArray = (JSONArray) jsonObject.get("mapObjects");
        for (Object o : natureObjectsArray) {
            JSONObject natureObject = (JSONObject) o;
            JSONArray position = (JSONArray) natureObject.get("position");
            int x = ((Long) (position.get(0))).intValue();
            int y = ((Long) (position.get(1))).intValue();
            natureObjects.add(new NatureObject((String) natureObject.get("type"), new Pair(x, y)));
        }
        return natureObjects;
    }

    public static void saveMap(Map map, int mapID) ///Saving Map With ObjectStreams into files
    {
        try {
            File mapFile = new File("Resources/maps/map" + mapID + ".map");
            FileOutputStream fileOutputStream = new FileOutputStream(mapFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map loadMap(int mapID) {/// Loading Map Files which were saved with ObjectStreams
        Map map = null;
        try {
            File mapFile = new File("Resources/maps/map" + mapID + ".map");
            FileInputStream fileInputStream = new FileInputStream(mapFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static JSONObject mapTilesToJSON(int mapId) {///Gives MapTile Array and Environment of map in JSON type
        MapTile[][] tilesArray = getMapTiles(mapId);
        JSONObject mapTileJSON = new JSONObject();
        JSONArray tiles = new JSONArray();
        for (int i = 0; i < Settings.MAP_WIDTH_RESOLUTION; i++) {
            for (int j = 0; j < Settings.MAP_HEIGHT_RESOLUTION; j++) {
                JSONObject tile = new JSONObject();
                JSONObject position = new JSONObject();
                position.put("x", i);
                position.put("y", j);
                tile.put("position", position);
                tile.put("type", tilesArray[i][j].type);
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
            ///adding id to object
            object.put("id", gameObject.id);
            ///adding position to object
            position.put("x", gameObject.position.x);
            position.put("y", gameObject.position.y);
            object.put("position", position);
            ///adding ownerName to object
            object.put("ownerName", gameObject.ownerName);
            ///adding health
            object.put("health", gameObject.health);
            ///adding object type
            object.put("type", gameObject.type);
            if (gameObject instanceof Human)
                object.put("canClimb", ((Human) gameObject).canClimb);
            objects.add(object);
        }
        mapObjectsJSON.put("objects", objects);
        return mapObjectsJSON;
    }

    public Map JSONObjectsToMap(JSONObject objectArray, Map map) {
        GameObject addingObject = null;
        Integer i;
        Integer j;
        String ownerName;
        Integer health;
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
                addingObject.health = health;
                addingObject.position.x = i;
                addingObject.position.y = j;
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
