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
                String line  = bufferedReader.readLine();
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
    public void saveMap(Map map,int mapID) ///Saving Map With ObjectStreams into files
    {
        try
        {
            File mapFile = new File("../../Resources/maps/map"+mapID+".map");
            FileOutputStream fileOutputStream = new FileOutputStream(mapFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Map LoadMap(int mapID) /// Loading Map Files which were saved with ObjectStreams
    {
        Map map=null;
        try
        {
            File mapFile = new File("../../Resources/maps/map"+mapID+".map");
            FileInputStream fileInputStream = new FileInputStream(mapFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            map = (Map) objectInputStream.readObject();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return map;
    }

    public JSONObject mapToJSON (Map map) {
        JSONObject finalJSON = new JSONObject();
        JSONObject pos = new JSONObject();
        JSONArray tiles = new JSONArray();
        JSONArray objects = new JSONArray();
        JSONArray tile = new JSONArray();
        JSONObject object = new JSONObject();
        //Tiles array to JSONarray
        //0 is Plain, 1 is Mountain, 2 is Sea
        for(int i=0;i<Settings.MAP_WIDTH_RESOLUTION;i++)
        {
            for (int j=0;j<Settings.MAP_HEIGHT_RESOLUTION;j++)
            {
                JSONObject object1=(JSONObject) object.clone();
                JSONArray oneTile=(JSONArray) tile.clone();
                JSONObject position = (JSONObject) pos.clone();
                ///adding position of tile into position object
                position.put("x",i);
                position.put("y",j);
                object1.put("position",position);
                oneTile.add(object1);
                ///adding plain
                if(map.tiles[i][j] instanceof Plain)
                {
                    oneTile.add(0);
                }
                ///adding Mountain
                else if(map.tiles[i][j] instanceof Mountain)
                {
                    oneTile.add(1);
                }
                ///adding sea
                else
                {
                    oneTile.add(2);
                }
                tiles.add(oneTile);
            }
        }
        for (GameObject gameObject : map.objects) {
            JSONObject object1=(JSONObject) object.clone();
            JSONObject position = (JSONObject) pos.clone();
            ///adding position to object
            position.put("x",gameObject.position.x);
            position.put("y",gameObject.position.y);
            object1.put("position",position);
            ///adding name to object
            object1.put("ownerName",gameObject.ownerName);
            ///adding object type
            if(gameObject instanceof Barracks)
            {
                object1.put("type","Barracks");
            }
            else if(gameObject instanceof Farm)
            {
                object1.put("type","Farm");
            }
            else if(gameObject instanceof Market)
            {
                object1.put("type","Market");
            }
            else if(gameObject instanceof Palace)
            {
                object1.put("type","Palace");
            }
            else if(gameObject instanceof Port)
            {
                object1.put("type","Port");
            }
            else if(gameObject instanceof Quarry)
            {
                object1.put("type","Quarry");
            }
            else if(gameObject instanceof WoodCutter)
            {
                object1.put("type","WoodCutter");
            }
            else if(gameObject instanceof Soldier)
            {
                object1.put("type","Soldier");
            }
            else if(gameObject instanceof Vassal)
            {
                object1.put("type","Vassal");
            }
            else if(gameObject instanceof Worker)
            {
                object1.put("type","Worker");
            }
            objects.add(object1);
        }
        finalJSON.put("tiles",tiles);
        finalJSON.put("objects",objects);
        return finalJSON;
    }
}
