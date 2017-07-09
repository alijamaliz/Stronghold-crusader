package StrongholdCrusader.GameObjects;

import StrongholdCrusader.Map.MapGUI;
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
    public MapGUI mapGUI;

    public GameObject() {
        health = 100;
    }

    public GameObject(MapGUI mapGUI) {
        health = 1000;
        this.mapGUI = mapGUI;
    }

    public abstract AnchorPane clickAction(boolean owner);
}
