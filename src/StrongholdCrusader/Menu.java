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
    public void start(Stage primaryStage)  {
        Menu.stage = primaryStage;
        stage.setTitle("Menu");
        try {
            Menu.stage.setScene(new Scene
                    ( FXMLLoader.load(getClass().getResource("ui/menu.fxml"))));


        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
