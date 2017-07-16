package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Network.GameEvent;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Baran on 5/29/2017.
 */
public class Market extends Building {


    public Market() {
        this.type = "Market";
        this.size = new Pair(4, 4);
        this.health = Settings.MARKET_INITIAL_HEALTH;
    }

    public Market(MapGUI mapGUI) {
        super(mapGUI);
        setImage(ResourceManager.getImage("Market"));
        this.type = "Market";
        this.size = new Pair(4, 4);
        this.health = Settings.MARKET_INITIAL_HEALTH;
    }


    public AnchorPane anchorPane;
    Button food;
    Button iron;
    Button wood;
    Button buy;
    Button sell;
    Button back;
    Button destroy;
    ImageView imageView;
    ProgressBar healthBar;


    public AnchorPane objectsMenuAnchorPane(boolean owner) {

        initializeAnchorPane();
        transition(destroy);
        transition(back);
        transition(buy);
        transition(sell);
        transition(food);
        transition(wood);
        transition(iron);

        food.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                food.setVisible(false);
                wood.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                back.setVisible(true);
                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mapGUI.map.sendGameEvent(GameEvent.BUY_RESOURCE,"food");
                    }
                });
                sell.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mapGUI.map.sendGameEvent(GameEvent.SELL_RESOURCE,"food");
                    }
                });

            }
        });

        wood.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wood.setVisible(false);
                food.setVisible(false);
                iron.setVisible(false);
                buy.setVisible(true);
                sell.setVisible(true);
                back.setVisible(true);
                buy.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mapGUI.map.sendGameEvent(GameEvent.BUY_RESOURCE,"wood");
                    }
                });
                sell.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mapGUI.map.sendGameEvent(GameEvent.SELL_RESOURCE,"wood");
                    }
                });

            }
        });

        iron.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                food.setVisible(false);
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
        destroy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Market.this.mapGUI.removeBuildings(Market.this);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                iron.setVisible(true);
                wood.setVisible(true);
                food.setVisible(true);
                buy.setVisible(false);
                sell.setVisible(false);
                back.setVisible(false);
            }
        });

        if (!owner){
            destroy.setVisible(false);
            back.setVisible(false);
            buy.setVisible(false);
            sell.setVisible(false);
            food.setVisible(false);
            wood.setVisible(false);
            iron.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        imageView = new ImageView(ResourceManager.getImage("Market"));
        food = new Button("food");
        iron = new Button("iron");
        wood = new Button("wood");
        buy = new Button("buy(5 piece)");
        sell = new Button("sell(5 piece)");
        back = new Button("Back");
        destroy = new Button("Destroy Building");
        back.setVisible(false);
        buy.setVisible(false);
        sell.setVisible(false);
        buy.setId("marketBuy");
        sell.setId("marketSell");
        food.setId("food");
        wood.setId("wood");
        iron.setId("iron");
        destroy.setGraphic(imageView);
        destroy.setLayoutX(50);
        destroy.setLayoutY(10);
        iron.setLayoutX(400);
        wood.setLayoutX(550);
        food.setLayoutX(700);
        food.setLayoutY(60);
        wood.setLayoutY(60);
        iron.setLayoutY(60);
        buy.setLayoutX(400);
        sell.setLayoutX(550);
        sell.setLayoutY(60);
        buy.setLayoutY(60);
        back.setLayoutX(700);
        back.setLayoutY(60);
        healthBar = new ProgressBar((double)this.health/Settings.MARKET_INITIAL_HEALTH);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHORPANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100,20);
        anchorPane.getChildren().addAll( destroy, food, wood, iron, buy, sell, back,healthBar);
        anchorPane.setId("marketMenu");
        anchorPane.setPrefSize(Settings.MENUS_ANCHORPANE_WIDTH,Settings.MENUS_ANCHORPANE_HEIGHT);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/market.css");
    }

    public void transition (Node button) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(100), button);
        transition.setAutoReverse(true);
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1);
                transition.setToX(1.1);
                transition.setFromY(1);
                transition.setToY(1.1);
                transition.play();
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                transition.setFromX(1.1);
                transition.setToX(1);
                transition.setFromY(1.1);
                transition.setToY(1);
                transition.play();
            }
        });
    }


}
