package StrongholdCrusader.GameObjects.Buildings;
import StrongholdCrusader.GameObjects.*;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Baran on 5/29/2017.
 */
public abstract class Building extends GameObject {
    Pair size;
    public AnchorPane anchorPane;
    abstract AnchorPane clickAction();
}
