package StrongholdCrusader;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class Menu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene
                ( FXMLLoader.load(getClass().getResource("/ui/MenuBG.fxml"))));
        stage.show();
    }
}
