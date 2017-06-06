import StrongholdCrusader.ClientPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Alireza on 6/5/2017.
 */
public class JoinGUI implements Initializable {

    @FXML
    Button join;
    @FXML
    TextField playerName;
    @FXML
    Label notice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        join.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playerName != null) {
                    ClientPlayer clientPlayer = new ClientPlayer(playerName.getText());
                }
                else
                    notice.setText("Please insert your name");
            }
        });

    }
}
