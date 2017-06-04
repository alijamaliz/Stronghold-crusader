package StrongholdCrusader;

import StrongholdCrusader.GameObjects.Buildings.Barracks;
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
    }
    public Image getImage(String name) ///Returns every object's Image
    {
        if(resources.containsKey(name))
        {
            return resources.get(name);
        }
        else return null;
    }
    /*public Image getBuilding(String name)
    {
        switch (name)
        {
            case "Barracks" : {
                File tmp = new File("Resources/images/Buildings/Barracks.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Farm" : {
                File tmp = new File("Resources/images/Buildings/Farm.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Market" : {
                File tmp = new File("Resources/images/Buildings/Market.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Palace" : {
                File tmp = new File("Resources/images/Buildings/Palace.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Port" : {
                File tmp = new File("Resources/images/Buildings/Port.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Quarry" : {
                File tmp = new File("Resources/images/Buildings/Barracks.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "WoodCutter" : {
                File tmp = new File("Resources/images/Buildings/WoodCutter.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            default: return null;
        }
    }
    public Image getHuman(String name)
    {
        switch (name)
        {
            case "Soldier" : {
                File tmp = new File("Resources/images/Humans/Soldier.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Worker" : {
                File tmp = new File("Resources/images/Humans/Worker.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Vassal" : {
                File tmp = new File("Resources/images/Humas/Vassal.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            default: return null;
        }
    }*/
}
