//ITNOA
package StrongholdCrusader;

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

        try {
            Menu.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("UI/menu.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Menu.stage.show();
    }
}
