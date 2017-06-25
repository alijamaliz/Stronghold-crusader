package StrongholdCrusader.Map;
import java.io.Serializable;
import java.util.LinkedList;
import StrongholdCrusader.GameObjects.Pair;
/**
 * Created by Baran on 5/29/2017.
 */
public abstract class MapTile implements Serializable {
    public String type;
    public boolean filled;
    public Pair position;
    public MapTile() {
        filled = false;
    }
    public MapTile(int x,int y)
    {
        filled=false;
        position = new Pair(x,y);
    }
}
