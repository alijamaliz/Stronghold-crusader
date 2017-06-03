package StrongholdCrusader.Map;

import StrongholdCrusader.Settings;

import java.io.*;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class MapManager {
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
}
