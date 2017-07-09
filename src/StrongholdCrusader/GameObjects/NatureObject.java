package StrongholdCrusader.GameObjects;

import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Created by MiladIbra on 7/9/2017.
 */
public class NatureObject extends ImageView implements Runnable {
    public String type;
    public Pair position;
    ResourceManager resourceManager;

    public NatureObject(String type, Pair pair) {
        resourceManager = new ResourceManager();
        this.type = type;
        this.position = pair;
        this.setLayoutX(position.x * Settings.MAP_TILES_WIDTH);
        this.setLayoutY(position.y * Settings.MAP_TILES_HEIGHT);
        new Thread(this).start();
    }

    @Override
    public void run() {
        if (this.type.equals("tree1")) {
            int counter = 1;
            while (true) {
                this.setImage(resourceManager.getImage("tree1Anim" + counter));
                if (counter < 13)
                    counter++;
                else
                    counter = 1;
                try {
                    Thread.sleep(1000 / Settings.FRAME_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.type.equals("tree2")) {
            int counter = 1;
            while (true) {
                this.setImage(resourceManager.getImage("tree2Anim" + counter));
                if (counter < 13)
                    counter++;
                else
                    counter = 1;
                try {
                    Thread.sleep(1000 / Settings.FRAME_RATE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
