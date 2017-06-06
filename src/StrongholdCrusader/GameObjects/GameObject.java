package StrongholdCrusader.GameObjects;

import java.io.Serializable;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class GameObject implements Serializable {
    public Pair position;
    int health;
    public String name;
}
