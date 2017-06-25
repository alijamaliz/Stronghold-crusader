package StrongholdCrusader.GameObjects.Humans;
import StrongholdCrusader.GameObjects.*;
import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Map.MapTile;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class Human extends GameObject {
    int foodUse,goldUse;
    int power;
    boolean can_climb;
    int speed;
    public void attack(GameObject object)
    {
        if (this.health > 0 && object.health>0)
        {
           if(object.health-power<0)
           {
               object.health=0;
           }
           else {
               object.health-=power;
           }
        }
    }
    public void goToTile(MapTile tile)
    {
        //TODO
    }
    public LinkedList<MapTile> findRoute(MapTile start , MapTile end)
    {
        //TODO
        return null;
    }
    public LinkedList<MapTile> adjacentTiles(Map map,MapTile tile)
    {
        LinkedList<MapTile> adjacent_tiles = new LinkedList<>();
        try{
            adjacent_tiles.add(map.tiles[tile.position.x-1][tile.position.y]);
        }catch (NullPointerException e) {}
        try{
            adjacent_tiles.add(map.tiles[tile.position.x+1][tile.position.y]);
        }catch (NullPointerException e) {}
        try{
            adjacent_tiles.add(map.tiles[tile.position.x][tile.position.y-1]);
        }catch (NullPointerException e) {}
        try{
            adjacent_tiles.add(map.tiles[tile.position.x-1][tile.position.y+1]);
        }catch (NullPointerException e) {}
        return adjacent_tiles;
    }
    public abstract LinkedList<MapTile> territory(Map map,MapTile tile);
}
