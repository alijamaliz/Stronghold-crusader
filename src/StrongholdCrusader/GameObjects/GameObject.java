package StrongholdCrusader.GameObjects;

import StrongholdCrusader.Map.MapGUI;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class GameObject extends ImageView implements Serializable {
    public int id;
    public Pair position;
    public int health;
    public String ownerName;
    public String type;
    public MapGUI mapGUI;

    public LinkedList<Pair> animationNextPositions;
    public Pair animationNextPosition;

    public GameObject() {
        animationNextPositions = new LinkedList<>();
        health = 100;
    }

    public GameObject(MapGUI mapGUI) {
        animationNextPositions = new LinkedList<>();
        health = 1000;
        this.mapGUI = mapGUI;
    }


}
