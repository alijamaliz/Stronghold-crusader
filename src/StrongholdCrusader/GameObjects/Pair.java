package StrongholdCrusader.GameObjects;

import java.io.Serializable;

/**
 * Created by Baran on 5/29/2017.
 */
public class Pair implements Serializable {
    public int x, y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
