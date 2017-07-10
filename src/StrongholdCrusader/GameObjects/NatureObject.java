package StrongholdCrusader.GameObjects;

import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by MiladIbra on 7/9/2017.
 */
public class NatureObject extends Group implements Runnable {
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
            createAnimation("tree1Anim", 13);
        }
        if (this.type.equals("tree2")) {
            createAnimation("tree2Anim", 13);
        }
    }

    private void createAnimation(String imagePrefix, int framesCount) {
        KeyFrame[] keyFrames = new KeyFrame[framesCount];
        for (int i = 0; i < framesCount; i++) {
            int finalI = i;
            keyFrames[i] = new KeyFrame(Duration.millis((1000 / Settings.FRAME_RATE) * (i + 1)), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    NatureObject.this.getChildren().setAll(new ImageView(resourceManager.getImage(imagePrefix + (finalI + 1))));
                }
            });
        }
        TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(keyFrames).build().play();
    }
}