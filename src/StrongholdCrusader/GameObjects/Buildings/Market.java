package StrongholdCrusader.GameObjects.Buildings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Market extends Building {
    AnchorPane anchorPane;
    @Override
    AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Market.png");
        ImageView imageView = new ImageView(building.toURI().toString());
        Button stone = new Button("stone");
        Button iron = new Button("iron");
        Button wood = new Button("wood");
        Button buy = new Button("buy");
        Button sell = new Button("sell");
        buy.setVisible(false);
        sell.setVisible(false);
        stone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stone.setVisible(false);
                wood.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                sell.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

            }
        });

        wood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wood.setVisible(false);
                stone.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                sell.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

            }
        });

        iron.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stone.setVisible(false);
                wood.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                sell.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

            }
        });

        Button distroy = new Button("Distroy Building");
        distroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        anchorPane.getChildren().addAll(imageView,distroy,stone,wood,iron,buy,sell);
        return anchorPane;
    }
}
