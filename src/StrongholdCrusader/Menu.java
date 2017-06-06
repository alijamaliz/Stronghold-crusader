package StrongholdCrusader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class Menu extends Application  {
    public static Stage stage;
    public static Stage window;
    public static Parent root;
    Thread thread;
    @Override
    public void start  (Stage stage)  {
        window = stage;
        stage.setTitle("Menu");
        try {
                root = FXMLLoader.load(getClass().getResource("ui/menu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        window.setScene(new Scene(root));
        window.show();


    }



}
