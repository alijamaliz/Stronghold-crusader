package StrongholdCrusader.GameObjects;

import javafx.scene.layout.AnchorPane;

import java.io.Serializable;

/**
 * Created by MiladIbra on 7/9/2017.
 */
public class NatureObject implements Serializable
{
    public String type;
    public Pair position;
    NatureObject(String type,Pair pair)
    {
        this.type = type;
        this.position=pair;
    }
}
