package StrongholdCrusader.GameObjects;

import StrongholdCrusader.ResourceManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.Serializable;

/**
 * Created by MiladIbra on 7/9/2017.
 */
public class NatureObject extends ImageView implements Serializable,Runnable
{
    public String type;
    public Pair position;
    public NatureObject(String type,Pair pair)
    {
        this.type = type;
        this.position=pair;
    }

    @Override
    public void run()
    {
        if(this.type=="tree")
        {
            int counter = 1;
            while (true) {
                this.setImage(ResourceManager.getImage("treeAnim"+counter));
                if (counter < 15)
                    counter++;
                else
                    counter = 0;
                try {
                    Thread.sleep(41);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
