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
    Button join;
    @FXML
    Button exit;
    @FXML
    TextField visible1;
    @FXML
    TextField visible2;
    @FXML
    Label lbl1;
    @FXML
    Label lbl2;
    @FXML
    Button submit;
    @FXML
    Label notice;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/images/menu/menu.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);


        server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                server.setVisible(false);
                join.setVisible(false);
                exit.setVisible(false);
                lbl1.setText("your name:");
                lbl2.setText("mapID:");
                lbl1.setLayoutX(lbl1.getLayoutX()+100);
                lbl2.setLayoutX(lbl2.getLayoutX()+100);
                visible1.setLayoutX(visible1.getLayoutX()+100);
                visible2.setLayoutX(visible2.getLayoutX()+100);
                submit.setLayoutX(submit.getLayoutX()+100);
                visible1.setVisible(true);
                visible2.setVisible(true);
                submit.setVisible(true);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (visible1 == null || visible2 == null)
                            notice.setText("Please insert values");
                        else {
                            Server server = new Server(Integer.parseInt(visible2.getText()));
                            String serverIP = server.getServerIP();
                            ClientPlayer clientPlayer = new ClientPlayer(visible1.getText(), serverIP);
                        }
                    }
                });
            }
        });
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                server.setVisible(false);
                join.setVisible(false);
                exit.setVisible(false);
                lbl1.setText("your name");
                lbl2.setText("IP");
                lbl1.setLayoutX(lbl1.getLayoutX()+100);
                lbl2.setLayoutX(lbl2.getLayoutX()+100);
                visible1.setLayoutX(visible1.getLayoutX()+100);
                visible2.setLayoutX(visible2.getLayoutX()+100);
                submit.setLayoutX(submit.getLayoutX()+100);
                visible1.setVisible(true);
                visible2.setVisible(true);
                submit.setVisible(true);
                submit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ClientPlayer clientPlayer = new ClientPlayer(visible1.getText(),visible2.getText());
                    }
                });
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
