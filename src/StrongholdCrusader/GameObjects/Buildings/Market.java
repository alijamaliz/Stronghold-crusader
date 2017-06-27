package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Market extends Building {
    public Market() {
        this.type = "Market";
        this.size = new Pair(4, 4);
    }

    public AnchorPane anchorPane;

    @Override
    public AnchorPane clickAction() {
        anchorPane = new AnchorPane();
        File building = new File("Resources/images/Buildings/Market.png");
        ImageView buildingImage = new ImageView(building.toURI().toString());
        Button stone = new Button("stone");
        Button iron = new Button("iron");
        Button wood = new Button("wood");
        Button buy = new Button("buy(5 piece)");
        Button sell = new Button("sell(5 piece)");
        Button back = new Button("Back");
        back.setVisible(false);
        buy.setVisible(false);
        sell.setVisible(false);
        buy.setId("marketBuy");
        sell.setId("marketSell");
        stone.setId("stone");
        wood.setId("wood");
        iron.setId("iron");
        stone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stone.setVisible(false);
                wood.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                back.setVisible(true);
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
                back.setVisible(true);
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
                back.setVisible(true);
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
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                iron.setVisible(true);
                wood.setVisible(true);
                stone.setVisible(true);
                buy.setVisible(false);
                sell.setVisible(false);
                back.setVisible(false);
            }
        });

        buildingImage.setLayoutX(30);
        buildingImage.setLayoutY(30);
        distroy.setLayoutX(30);
        distroy.setLayoutY(170);
        iron.setLayoutX(200);
        wood.setLayoutX(300);
        stone.setLayoutX(400);
        stone.setLayoutY(80);
        wood.setLayoutY(80);
        iron.setLayoutY(80);
        buy.setLayoutX(200);
        sell.setLayoutX(350);
        sell.setLayoutY(120);
        buy.setLayoutY(120);
        back.setLayoutX(300);
        back.setLayoutY(200);
        anchorPane.getChildren().addAll(buildingImage, distroy, stone, wood, iron, buy, sell, back);
        anchorPane.setLayoutX(10);
        anchorPane.setLayoutY(Screen.getPrimary().getBounds().getHeight()-270);
        anchorPane.setId("marketMenu");
        anchorPane.getStylesheets().add("StrongholdCrusader/css/market.css");
        return anchorPane;
    }
}
