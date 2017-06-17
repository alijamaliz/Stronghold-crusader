package StrongholdCrusader;

import StrongholdCrusader.GameObjects.Buildings.Barracks;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Baran on 5/29/2017.
 */
public class ResourceManager {
    Map<String,Image> resources;
    public ResourceManager()
    {
        resources = new HashMap<>();
        ///Adding Barracks Photo
        File barracksFile = new File("Resources/images/Buildings/Barracks.png");
        Image barracks = new Image(barracksFile.toURI().toString());
        resources.put("Barracks",barracks);
        ///Adding Farm Photo
        File farmFile = new File("Resources/images/Buildings/Farm.png");
        Image farm = new Image(farmFile.toURI().toString());
        resources.put("Farm",farm);
        ///Adding Market Photo
        File marketFile = new File("Resources/images/Buildings/Market.png");
        Image market = new Image(marketFile.toURI().toString());
        resources.put("Market",market);
        ///Adding Palace Photo
        File PalaceFile = new File("Resources/images/Buildings/Palace.png");
        Image palace = new Image(PalaceFile.toURI().toString());
        resources.put("Palace",palace);
        ///Adding Port Photo
        File portFile = new File("Resources/images/Buildings/Port.png");
        Image port = new Image(portFile.toURI().toString());
        resources.put("Port",port);
        ///Adding Quarry Photo
        File quarryFile = new File("Resources/images/Buildings/Quarry.png");
        Image quarry = new Image(quarryFile.toURI().toString());
        resources.put("Quarry",quarry);
        ///Adding WoodCutter Photo
        File woodcutterFile = new File("Resources/images/Buildings/WoodCutter.png");
        Image woodcutter = new Image(woodcutterFile.toURI().toString());
        resources.put("WoodCutter",woodcutter);
        ///Adding Soldier Photo
        File soldierFile = new File("Resources/images/Humans/Soldier.png");
        Image soldier = new Image(soldierFile.toURI().toString());
        resources.put("Soldier",soldier);
        ///Adding Worker Photo
        File workerFile = new File("Resources/images/Humans/Worker.png");
        Image worker = new Image(workerFile.toURI().toString());
        resources.put("Worker",worker);
        ///Adding Vassal Photo
        File vassalFile = new File("Resources/images/Humans/Worker.png");
        Image vassal = new Image(vassalFile.toURI().toString());
        resources.put("Vassal",vassal);

        File plain1TileFile = new File("Resources/images/Tiles/Plain1.jpg");
        Image plain1Tile = new Image(plain1TileFile.toURI().toString());
        resources.put("Plain1", plain1Tile);

        File mountainTileFile = new File("Resources/images/Tiles/Mountain1.jpg");
        Image mountainTile = new Image(mountainTileFile.toURI().toString());
        resources.put("Mountain", mountainTile);

        File seaTileFile = new File("Resources/images/Tiles/Sea1.jpg");
        Image seaTile = new Image(seaTileFile.toURI().toString());
        resources.put("Sea", seaTile);
    }
    public Image getImage(String name) ///Returns every object's Image
    {
        if(resources.containsKey(name))
        {
            return resources.get(name);
        }
        else return null;
    }
    public Cursor getCursor(String name) {
        Image image = getImage(name);
        ImageCursor imageCursor = new ImageCursor(image);
        return imageCursor;
    }
}
