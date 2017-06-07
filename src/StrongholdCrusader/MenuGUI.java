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
    @FXML
    Button back;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/images/menu/menu.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);

        lbl1.setVisible(false);
        lbl2.setVisible(false);
        notice.setVisible(false);
        visible1.setVisible(false);
        visible2.setVisible(false);
        back.setVisible(false);

        server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToCreateServerPage();
            }
        });
        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToJoinToServerPage();
            }
        });

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                backToMainMenu();
            }
        });


    }

    private void backToMainMenu() {
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        notice.setVisible(false);
        visible1.setVisible(false);
        visible2.setVisible(false);
        back.setVisible(false);
        submit.setVisible(false);

        notice.setText("");

        server.setVisible(true);
        join.setVisible(true);
        exit.setVisible(true);
    }

    private void goToCreateServerPage() {
        server.setVisible(false);
        join.setVisible(false);
        exit.setVisible(false);

        lbl1.setText("نام کاربری:");
        lbl1.setVisible(true);

        lbl2.setText("شماره نقشه:");
        lbl2.setVisible(true);

        visible1.setPromptText("ex: Ali");
        visible1.setVisible(true);

        visible2.setPromptText("ex: 1");
        visible2.setVisible(true);

        submit.setVisible(true);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (visible1.getText().equals("") || visible2.getText().equals("")) {
                    notice.setText("* مقادیر وارد شده صحیح نیست...");
                    notice.setVisible(true);
                }
                else {
                    //Create new server and player
                    Server server = new Server(Integer.parseInt(visible2.getText()));
                    String serverIP = server.getServerIP();
                    ClientPlayer clientPlayer = new ClientPlayer(visible1.getText(), serverIP);
                }
            }
        });

        back.setVisible(true);
    }

    private void goToJoinToServerPage() {
        server.setVisible(false);
        join.setVisible(false);
        exit.setVisible(false);

        lbl1.setText("نام کاربری:");
        lbl1.setVisible(true);

        lbl2.setText("آدرس سرور:");
        lbl2.setVisible(true);

        visible1.setPromptText("ex: Ali");
        visible1.setVisible(true);

        visible2.setPromptText("ex: 127.0.0.1");
        visible2.setVisible(true);

        submit.setVisible(true);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Create new player
                ClientPlayer clientPlayer = new ClientPlayer(visible1.getText(),visible2.getText());
            }
        });

        back.setVisible(true);
    }
}
