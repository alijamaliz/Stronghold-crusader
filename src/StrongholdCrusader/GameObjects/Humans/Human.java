package StrongholdCrusader.GameObjects.Humans;
import StrongholdCrusader.GameObjects.*;
import StrongholdCrusader.Map.*;
import StrongholdCrusader.Settings;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class Human extends GameObject {
    int zone;
    int foodUse,goldUse;
    int power;
    protected boolean canClimb;
    protected int speed;
    private LinkedList<MapTile> movingPath;
    private int speedHandler;

    public Human() {
    }

    public Human(MapGUI mapGUI) {
        super(mapGUI);
    }

    public void attack(MapTile[][] tiles ,GameObject object,MapTile objectTile)
    {
        int x = this.position.x;
        int y = this.position.y;
        LinkedList<Pair> zone = new LinkedList<>();
        zone.add(new Pair(x-1,y));
        zone.add(new Pair(x+1,y));
        zone.add(new Pair(x,y-1));
        zone.add(new Pair(x,y+1));
        zone.add(new Pair(x,y));
        Pair objectPair = new Pair(object.position.x,object.position.y);
        if(!Pair.contains(zone,objectPair))
        {
            this.goToTile(tiles,objectTile);
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
        else {
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
    }

    public void updatePosition() {
        if (speedHandler == Settings.SEND_DATA_RATE / speed) {
            if (movingPath != null) {
                if (movingPath.size() != 0) {
                    this.position = movingPath.getLast().position;
                    movingPath.removeLast();
                } else {
                    movingPath = null;
                }
            }
            speedHandler = 0;
        }
        speedHandler++;
    }

    public void goToTile(MapTile[][] tiles, MapTile tile)
    {
        this.movingPath = findRoute(tiles, tiles[position.x][position.y], tile, canClimb);
        //TODO
    }
    public LinkedList<MapTile> territory(MapTile[][] tiles, MapTile tile) ///Return Zone of every Human
    {
        LinkedList<MapTile> territory = new LinkedList<>();
        int x = tile.position.x;
        int y = tile.position.y;
        for(int i = x-zone;i<=x+zone;i++)
        {
            for(int j = y-(i-x+zone);j<=y+(i-x+zone);j++)
            {
                try {
                    territory.add(tiles[i][j]);
                }catch (Exception e)
                {
                    ///Do Nothing , Becuase this Tile is Out of array
                }
            }
        }
        return territory;
    }
    public abstract void useResources();

    private static LinkedList<MapTile> adjacentList(MapTile[][] tiles, MapTile tile, boolean canClimb)
    {
        LinkedList<MapTile> adjacents = new LinkedList<>();
        for (int i = tile.position.x-1 ;i<tile.position.x+2;i++)
        {
            for (int j =tile.position.y-1 ; j<tile.position.y+2;j++)
            {
                if((i== tile.position.x && j!=tile.position.y) || (j==tile.position.y && i!=tile.position.x)) // Not adding corner tiles
                {
                    try
                    {
                        MapTile tmp = tiles[i][j];
                        if(!tmp.filled && canClimb) //for climbers
                        {
                            if(!(tmp instanceof Sea))
                            {
                                adjacents.add(tmp);
                            }
                        }
                        else if(!tmp.filled && !canClimb)
                        {
                            if(!(tmp instanceof Mountain) && !(tmp instanceof Sea)) // for non-climbers
                            {
                                adjacents.add(tmp);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        ///Do Nothing becuase its Null pionter and unwanted nodes
                    }
                }
            }
        }
        return adjacents;
    }

    private LinkedList<MapTile> findRoute(MapTile[][] tiles, MapTile start , MapTile end , boolean canClimb) ///BFS codes go here
    {
        HashMap<MapTile, Boolean> visited = new HashMap<>(); //every tile is visited or not
        HashMap<MapTile,MapTile> edgeTo = new HashMap<>(); // stores where the tile comes from
        LinkedList<MapTile> path = new LinkedList<>();
        Queue<MapTile> queue = new LinkedList();
        MapTile currunt = start;
        queue.add(currunt);
        visited.put(currunt,true);
        while (!queue.isEmpty())
        {
            currunt = queue.remove();
            if(currunt.equals(end))
            {
                break;
            }
            else
            {
                for (MapTile mapTile : adjacentList(tiles,currunt,canClimb)) {
                    if(!visited.containsKey(mapTile))
                    {
                        queue.add(mapTile);
                        visited.put(mapTile,true);
                        edgeTo.put(mapTile,currunt);
                    }
                }
            }
        }
        if(!currunt.equals(end)) // runs when there is not a way and return an empty LinkedList
        {
            return path;
        }
        for(MapTile tile = end ; tile!=null ; tile = edgeTo.get(tile)) // adding tiles to LinkedList (reversely)
        {
            path.addLast(tile);
        }
        return path;
    }
}
