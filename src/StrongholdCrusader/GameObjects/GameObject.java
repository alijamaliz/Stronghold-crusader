package StrongholdCrusader.GameObjects;

import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class GameObject implements Serializable {
    public int id;
    public Pair position;
    public int health;
    public String ownerName;
    public String type;

    public GameObject() {
        health = 100;
    }

    public abstract AnchorPane clickAction();
}
