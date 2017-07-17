//ITNOA//
package StrongholdCrusader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class Menu extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) {
        Menu.stage = stage;
        //Menu.stage.initStyle(StageStyle.UNDECORATED);

        Menu.stage.setOnCloseRequest(event -> System.exit(0));

        Scene scene;

        try {
            scene = new Scene(FXMLLoader.load(getClass().getResource("ui/menu.fxml")));
            Menu.stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Menu.stage.show();
    }
}
