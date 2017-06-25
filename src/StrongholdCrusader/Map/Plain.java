package StrongholdCrusader.Map;

import StrongholdCrusader.GameObjects.Pair;

import java.io.Serializable;

/**
 * Created by Baran on 5/29/2017.
 */
public class Plain extends MapTile implements Serializable {
    public Plain() {
        this.filled = false;
        this.type="Plain";
    }
    public Plain(int x,int y)
    {
        filled=false;
        this.position = new Pair(x,y);
        this.type="Plain";
    }
}
