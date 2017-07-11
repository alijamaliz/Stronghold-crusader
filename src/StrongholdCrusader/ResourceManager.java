package StrongholdCrusader;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Baran on 5/29/2017.
 */
public class ResourceManager {
    Map<String, Image> resources;
    Map<String, AudioClip> sounds;

    public ResourceManager() {
        resources = new HashMap<>();
        sounds = new HashMap<>();
        ///Adding Barracks Photo
        File barracksFile = new File("Resources/images/Buildings/Barracks.png");
        Image barracks = new Image(barracksFile.toURI().toString());
        resources.put("Barracks", barracks);
        ///Adding Farm Photo
        File farmFile = new File("Resources/images/Buildings/Farm.png");
        Image farm = new Image(farmFile.toURI().toString());
        resources.put("Farm", farm);
        ///Adding Market Photo
        File marketFile = new File("Resources/images/Buildings/Market.png");
        Image market = new Image(marketFile.toURI().toString());
        resources.put("Market", market);
        ///Adding Palace Photo
        File PalaceFile = new File("Resources/images/Buildings/Palace.png");
        Image palace = new Image(PalaceFile.toURI().toString());
        resources.put("Palace", palace);
        ///Adding Port Photo
        File portFile = new File("Resources/images/Buildings/Port.png");
        Image port = new Image(portFile.toURI().toString());
        resources.put("Port", port);
        ///Adding Quarry Photo
        File quarryFile = new File("Resources/images/Buildings/Quarry.png");
        Image quarry = new Image(quarryFile.toURI().toString());
        resources.put("Quarry", quarry);
        ///Adding WoodCutter Photo
        File woodcutterFile = new File("Resources/images/Buildings/WoodCutter.png");
        Image woodcutter = new Image(woodcutterFile.toURI().toString());
        resources.put("WoodCutter", woodcutter);
        ///Adding Soldier Photo
        File soldierFile = new File("Resources/images/Humans/Soldier.png");
        Image soldier = new Image(soldierFile.toURI().toString());
        resources.put("Soldier", soldier);
        ///Adding Worker Photo
        File workerFile = new File("Resources/images/Humans/Worker.png");
        Image worker = new Image(workerFile.toURI().toString());
        resources.put("Worker", worker);
        ///Adding Vassal Photo
        File vassalFile = new File("Resources/images/Humans/Vassal.png");
        Image vassal = new Image(vassalFile.toURI().toString());
        resources.put("Vassal", vassal);

        File barracksFileMenu = new File("Resources/images/Buildings/Barracks-menu.png");
        Image barracksMenu = new Image(barracksFileMenu.toURI().toString());
        resources.put("Barracks-menu", barracksMenu);

        File farmMenuFile = new File("Resources/images/Buildings/Farm-menu.png");
        Image farmMenu = new Image(farmMenuFile.toURI().toString());
        resources.put("Farm-menu", farmMenu);

        File marketMenuFile = new File("Resources/images/Buildings/Market-menu.png");
        Image marketMenu = new Image(marketMenuFile.toURI().toString());
        resources.put("Market-menu", marketMenu);

        File portMenuFile = new File("Resources/images/Buildings/Port-menu.png");
        Image portMenu = new Image(portMenuFile.toURI().toString());
        resources.put("Port-menu", portMenu);

        File quarryMenuFile = new File("Resources/images/Buildings/Quarry-menu.png");
        Image quarryMenu = new Image(quarryMenuFile.toURI().toString());
        resources.put("Quarry-menu", quarryMenu);

        File woodcutterMenuFile = new File("Resources/images/Buildings/WoodCutter-menu.png");
        Image woodcutterMenu = new Image(woodcutterMenuFile.toURI().toString());
        resources.put("WoodCutter-menu", woodcutterMenu);

        File plain1TileFile = new File("Resources/images/Tiles/plain1.jpg");
        Image plain1Tile = new Image(plain1TileFile.toURI().toString());
        resources.put("Plain1", plain1Tile);

        File mountainTileFile = new File("Resources/images/Tiles/Mountain1.png");
        Image mountainTile = new Image(mountainTileFile.toURI().toString());
        resources.put("Mountain", mountainTile);

        File seaTileFile = new File("Resources/images/Tiles/Sea1.jpg");
        Image seaTile = new Image(seaTileFile.toURI().toString());
        resources.put("Sea", seaTile);

        File selectDestinationFile = new File("Resources/images/Cursors/selectDestination.png");
        Image SelectDestination = new Image(selectDestinationFile.toURI().toString());
        resources.put("SelectDestination", SelectDestination);

        File refrencesImageFile = new File("Resources/images/resources-board.png");
        Image refrencesAnchorpane = new Image(refrencesImageFile.toURI().toString());
        resources.put("Refrences", refrencesAnchorpane);

        ///Animation of Tree

        for (int i = 1; i <= 14; i++) {
            File treeAnimFile = new File("Resources/images/animation/tree1/tree" + i + ".png");
            Image treeAnimImage = new Image(treeAnimFile.toURI().toString());
            resources.put("tree1Anim" + i, treeAnimImage);
        }

        for (int i = 1; i <= 14; i++) {
            File treeAnimFile = new File("Resources/images/animation/tree2/tree" + i + ".png");
            Image treeAnimImage = new Image(treeAnimFile.toURI().toString());
            resources.put("tree2Anim" + i, treeAnimImage);
        }
        ///Adding Sounds
        File bgMusic1 = new File("Resources/sounds/bgMusic1.mp3");
        AudioClip bgMusicClip1 = new AudioClip(bgMusic1.toURI().toString());
        bgMusicClip1.setCycleCount(AudioClip.INDEFINITE);
        sounds.put("bgMusic1",bgMusicClip1);

        File bgMusic2 = new File("Resources/sounds/bgMusic2.mp3");
        AudioClip bgMusicClip2 = new AudioClip(bgMusic2.toURI().toString());
        bgMusicClip1.setCycleCount(AudioClip.INDEFINITE);
        sounds.put("bgMusic2",bgMusicClip2);
    }


    public Image getImage(String name) ///Returns every object's Image
    {
        if (resources.containsKey(name)) {
            return resources.get(name);
        } else return null;
    }

    public AudioClip getSound(String name) {
        if (sounds.containsKey(name)) {
            return sounds.get(name);
        } else return null;
    }

    public Cursor getCursor(String name) {
        Image image = getImage(name);
        ImageCursor imageCursor = new ImageCursor(image);
        return imageCursor;
    }
}
