package StrongholdCrusader;

import StrongholdCrusader.Network.Server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class MenuGUI implements Initializable {

    @FXML
    ImageView image;
    @FXML
    Button server;
    @FXML
    Button join;
    @FXML
    Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Server server1 = new Server();
            }
        });
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientPlayer clientPlayer = new ClientPlayer("Ali");
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}
