package StrongholdCrusader.GameObjects;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public class Pair implements Serializable {
    public int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static boolean contains(LinkedList<Pair> list , Pair pair)
    {
        for (Pair pos : list) {
            if(pos.x == pair.x && pos.y==pair.y)
            {
                return true;
            }
        }
        return false;
    }
}
