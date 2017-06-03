package StrongholdCrusader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class Menu extends Application {
    @Override
    public void start(Stage stage)  {
        stage.setTitle("Menu");
        try {

            stage.setScene(new Scene
                    ( FXMLLoader.load(getClass().getResource("ui/menu.fxml"))));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
