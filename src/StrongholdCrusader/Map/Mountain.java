package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Pair;

import java.io.Serializable;

/**
 * Created by Baran on 5/29/2017.
 */
public class Mountain extends MapTile implements Serializable {
    public Mountain() {
        this.filled = false;
        this.type="Mountain";
    }
    public Mountain(int x,int y)
    {
        filled=false;
        this.position = new Pair(x,y);
        this.type="Mountain";
    }
}
