package StrongholdCrusader.GameObjects;

import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Created by MiladIbra on 7/9/2017.
 */
public class NatureObject extends Group implements Runnable {
    public String type;
    public Pair position;

    public NatureObject(String type, Pair pair) {
        this.type = type;
        this.position = pair;
        this.setLayoutX(position.x * Settings.MAP_TILES_WIDTH);
        this.setLayoutY(position.y * Settings.MAP_TILES_HEIGHT);
        if (this.type.equals("tree1")) {
            createAnimation("tree1Anim", 13);
        }
        if (this.type.equals("tree2")) {
            createAnimation("tree2Anim", 13);
        }
        if (this.type.equals("tree3")) {
            createAnimation("tree3Anim", 13);
        }
        if (this.type.equals("well")) {
            NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage("natureWell")));
        }
        if (this.type.equals("panel")) {
            NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage("naturePanel")));
        }
        if (this.type.equals("bush1")) {
            NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage("natureBush1")));
        }
        if (this.type.equals("cactus1")) {
            NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage("natureCactus1")));
        }
        if (this.type.equals("cactus2")) {
            NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage("natureCactus2")));
        }
    }

    @Override
    public void run() {

    }

    private void createAnimation(String imagePrefix, int framesCount) {
        KeyFrame[] keyFrames = new KeyFrame[framesCount];
        for (int i = 0; i < framesCount; i++) {
            int finalI = i;
            keyFrames[i] = new KeyFrame(Duration.millis((1000 / Settings.FRAME_RATE) * (i + 1)), event -> NatureObject.this.getChildren().setAll(new ImageView(ResourceManager.getImage(imagePrefix + (finalI + 1)))));
        }
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.play();
    }
}
