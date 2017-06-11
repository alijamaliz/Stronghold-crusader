package StrongholdCrusader;

import StrongholdCrusader.Network.GameEvent;
import StrongholdCrusader.Network.Server;
import StrongholdCrusader.Network.ServerPlayer;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.SourceAdapterChange;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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
    GridPane usersGridView;
    @FXML
    Button startGame;
    private ClientPlayer clientPlayer;
    private int numberOfUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("Resources/images/menu/menu.jpg");
        Image image1 = new Image(file.toURI().toString());
        image.setImage(image1);

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
        usersGridView.setVisible(true);
        showPlayersPane();

        startGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked");
                start();
            }
        });
        if (showStartButton)
            startGame.setVisible(true);

    }

    private void start() {
        clientPlayer.client.sendGameEvent(GameEvent.START_GAME, "Game started...");
        clientPlayer.map.showMapScreen();
    }

    private void showPlayersPane() {
        usersGridView.setPadding(new Insets(10));
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(100);
        usersGridView.getColumnConstraints().addAll(column1, column2);

        Label usernameLabel = new Label("نام کاربری");
        Label addressLabel = new Label("آدرس");
        addressLabel.setPrefWidth(150);
        usernameLabel.setPrefWidth(150);
        addressLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        usernameLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        usersGridView.add(usernameLabel, 0, 0);
        GridPane.setHalignment(addressLabel, HPos.RIGHT);
        usersGridView.add(addressLabel, 1, 2);
    }

    public void addPlayerToTable(String username, String address) {
        numberOfUsers++;
        System.out.println(username + ":" + address);
        System.out.println(numberOfUsers);
        Label usernameLabel = new Label(username);
        Label addressLabel = new Label(address);
        addressLabel.setPrefWidth(150);
        usernameLabel.setPrefWidth(150);

        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        //usersGridView.add(usernameLabel, 0, numberOfUsers + 1);
        GridPane.setHalignment(addressLabel, HPos.LEFT);
        //usersGridView.add(addressLabel, 1, numberOfUsers + 1);

    }
}
