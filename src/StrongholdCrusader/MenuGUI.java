package StrongholdCrusader;

import StrongholdCrusader.Network.GameEvent;
import StrongholdCrusader.Network.Server;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private ArrayList<String> players;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        players = new ArrayList<>();
        File file = new File("Resources/images/menu/menu_00000.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);

        KeyFrame[] keyFrames = new KeyFrame[300];
        for (int i = 0; i < 300; i++) {
            String imageNumber = String.valueOf(i);
            while (imageNumber.length() < 5)
                imageNumber = "0" + imageNumber;
            File f = new File("Resources/images/menu/menu_" + imageNumber + ".jpg");
            Image img = new Image(f.toURI().toString());
            keyFrames[i] = new KeyFrame(Duration.millis((1000 / Settings.FRAME_RATE) * (i + 1)), event -> image.setImage(img));
        }
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().addAll(keyFrames);
        timeline.play();

        lbl1.setVisible(false);
        lbl2.setVisible(false);
        notice.setVisible(false);
        visible1.setVisible(false);
        visible2.setVisible(false);
        back.setVisible(false);

        server.setOnAction(event -> goToCreateServerPage());
        join.setOnAction(event -> goToJoinToServerPage());
        exit.setOnAction(event -> System.exit(0));
        back.setOnAction(event -> backToMainMenu());
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
        submit.setOnAction(event -> {
            if (visible1.getText().equals("") || visible2.getText().equals("")) {
                notice.setText("* مقادیر وارد شده صحیح نیست...");
                notice.setVisible(true);
            } else {
                //Create new server and player
                Server server1 = new Server(Integer.parseInt(visible2.getText()));
                String serverIP = server1.getServerIP();
                clientPlayer = new ClientPlayer(visible1.getText(), serverIP, MenuGUI.this);
                serverIPLabel.setText("آدرس سرور: " + serverIP);
                goToUsersListPage(true);
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
        submit.setOnAction(event -> {
            //Create new player
            clientPlayer = new ClientPlayer(visible1.getText(), visible2.getText(), MenuGUI.this);
            goToUsersListPage(false);
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

        startGame.setOnAction(event -> start());
        if (showStartButton)
            startGame.setVisible(true);

    }

    private void start() {
        clientPlayer.client.sendGameEvent(GameEvent.START_GAME, "Game started...");
    }

    void addPlayerToTable(String username) {
        players.add(username);
        usersListView.setItems(FXCollections.observableList(players));
    }
}
