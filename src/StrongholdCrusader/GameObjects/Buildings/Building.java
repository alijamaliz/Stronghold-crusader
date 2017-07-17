package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.GameObject;
import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class Building extends GameObject {
    public Pair size;
    public AnchorPane anchorPane;

    public Building() {
    }

    public Building(MapGUI mapGUI) {
        super(mapGUI);
    }

    public abstract void initializeAnchorPane();

    public abstract AnchorPane objectsMenuAnchorPane(boolean owner);
}
