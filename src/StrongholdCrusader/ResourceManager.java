package StrongholdCrusader;

import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class ResourceManager {
    public Image getBuilding(String name)
    {
        switch (name)
        {
            case "Barracks" : {
                File tmp = new File("../Resources/images/Buildings/Barracks.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Farm" : {
                File tmp = new File("../Resources/images/Buildings/Farm.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Market" : {
                File tmp = new File("../Resources/images/Buildings/Market.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Palace" : {
                File tmp = new File("../Resources/images/Buildings/Palace.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Port" : {
                File tmp = new File("../Resources/images/Buildings/Port.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Quarry" : {
                File tmp = new File("../Resources/images/Buildings/Barracks.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "WoodCutter" : {
                File tmp = new File("../Resources/images/Buildings/WoodCutter.png");
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
                File tmp = new File("../Resources/images/Humans/Soldier.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Worker" : {
                File tmp = new File("../Resources/images/Humans/Worker.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            case "Vassal" : {
                File tmp = new File("../Resources/images/Humas/Vassal.png");
                Image image = new Image(tmp.toURI().toString());
                return image;
            }
            default: return null;
        }
    }
}
