package StrongholdCrusader;

import StrongholdCrusader.Network.GameEvent;
import StrongholdCrusader.Network.Server;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    Label serverIPLabel;
    @FXML
    ListView<String> usersListView;
    @FXML
    Button startGame;
    private ClientPlayer clientPlayer;
    private int numberOfUsers;
    private ArrayList<String> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = new ArrayList<>();
        File file = new File("Resources/images/menu/menu_00000.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);

        Thread animateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = 0;
                while (true) {
                    String imageNumber = String.valueOf(counter);
                    while (imageNumber.length() < 5)
                        imageNumber = "0" + imageNumber;

                    File file = new File("Resources/images/menu/menu_" + imageNumber + ".jpg");
                    Image image1 = new Image(file.toURI().toString());
                    image.setImage(image1);
                    if (counter < 299)
                        counter++;
                    else
                        counter = 0;
                    try {
                        Thread.sleep(41);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animateThread.start();


        numberOfUsers = 0;

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

        back.setVisible(true);

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
                } else {
                    //Create new server and player
                    Server server = new Server(Integer.parseInt(visible2.getText()));
                    String serverIP = server.getServerIP();
                    clientPlayer = new ClientPlayer(visible1.getText(), serverIP, MenuGUI.this);
                    serverIPLabel.setText("آدرس سرور: " + serverIP);
                    goToUsersListPage(true);
                }
            }
        });
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
                clientPlayer = new ClientPlayer(visible1.getText(), visible2.getText(), MenuGUI.this);
                goToUsersListPage(false);
            }
        });

        back.setVisible(true);
    }

    private void goToUsersListPage(boolean showStartButton) {
        lbl1.setVisible(false);
        lbl2.setVisible(false);
        visible1.setVisible(false);
        visible2.setVisible(false);
        submit.setVisible(false);
        back.setVisible(false);

        serverIPLabel.setVisible(true);
        usersListView.setVisible(true);

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start();
            }
        });
        if (showStartButton)
            startGame.setVisible(true);

    }

    private void start() {
        clientPlayer.client.sendGameEvent(GameEvent.START_GAME, "Game started...");
    }

    public void addPlayerToTable(String username) {
        numberOfUsers++;
        players.add(username);
        usersListView.setItems(FXCollections.observableList(players));
    }
}
