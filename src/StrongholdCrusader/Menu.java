//ITNOA
package StrongholdCrusader;

import StrongholdCrusader.GameObjects.Buildings.Barracks;
import StrongholdCrusader.GameObjects.Humans.Human;
import StrongholdCrusader.GameObjects.Humans.Soldier;
import StrongholdCrusader.GameObjects.Humans.Vassal;
import StrongholdCrusader.GameObjects.Humans.Worker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class Menu extends Application  {
    public static Stage stage;

    @Override
    public void start (Stage stage) {
        Menu.stage = stage;
        //Menu.stage.initStyle(StageStyle.UNDECORATED);
        Soldier soldier = new Soldier();
        soldier.clickAction();
        Scene scene = new Scene(soldier.anchorPane);
        Menu.stage.setScene(scene);

        /*try {
            Menu.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("ui/menu.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Menu.stage.show();
    }
}
