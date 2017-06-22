package StrongholdCrusader.Map;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class MapTile implements Serializable {
    String type;
    public boolean filled;

    public MapTile() {
        filled = false;
    }
}
