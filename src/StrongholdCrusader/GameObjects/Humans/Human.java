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
    public abstract LinkedList<MapTile> territory(Map map,MapTile tile);
    public abstract void useResources();
}
