package StrongholdCrusader;

import StrongholdCrusader.Network.Server;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by MiladIbra on 6/3/2017.
 */
public class MenuGUI implements Initializable {

    //mainmenu
    @FXML
    ImageView image;
    @FXML
    Button server;
    @FXML
    Button joinpage;
    @FXML
    Button exit;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/images/menu/menu.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);


        server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Server server1 = new Server();
            }
        });
        joinpage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Menu.root = FXMLLoader.load(getClass().getResource("ui/join.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Menu.window.setScene(new Scene(Menu.root));
                Menu.window.show();
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
