package StrongholdCrusader.GameObjects.Buildings;

import StrongholdCrusader.GameObjects.Pair;
import StrongholdCrusader.Map.MapGUI;
import StrongholdCrusader.Network.GameEvent;
import StrongholdCrusader.ResourceManager;
import StrongholdCrusader.Settings;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Created by Baran on 5/29/2017.
 */
public class Market extends Building {

    public AnchorPane anchorPane;
    private Button food;
    private Button wood;
    private Button buy;
    private Button sell;
    private Button back;
    private Button destroy;

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

    public AnchorPane objectsMenuAnchorPane(boolean owner) {
        initializeAnchorPane();
        transition(destroy);
        transition(back);
        transition(buy);
        transition(sell);
        transition(food);
        transition(wood);

        food.setOnAction(event -> {
            food.setVisible(false);
            wood.setVisible(false);
            buy.setVisible(true);
            sell.setVisible(true);
            back.setVisible(true);
            buy.setOnAction(event1 -> mapGUI.map.sendGameEvent(GameEvent.BUY_RESOURCE, "food"));
            sell.setOnAction(event12 -> mapGUI.map.sendGameEvent(GameEvent.SELL_RESOURCE, "food"));
        });

        wood.setOnAction(event -> {
            wood.setVisible(false);
            food.setVisible(false);
            buy.setVisible(true);
            sell.setVisible(true);
            back.setVisible(true);
            buy.setOnAction(event13 -> mapGUI.map.sendGameEvent(GameEvent.BUY_RESOURCE, "wood"));
            sell.setOnAction(event14 -> mapGUI.map.sendGameEvent(GameEvent.SELL_RESOURCE, "wood"));
        });

        destroy.setOnAction(event -> Market.this.mapGUI.removeBuildings(Market.this));
        back.setOnAction(event -> {
            wood.setVisible(true);
            food.setVisible(true);
            buy.setVisible(false);
            sell.setVisible(false);
            back.setVisible(false);
        });

        if (!owner) {
            destroy.setVisible(false);
            back.setVisible(false);
            buy.setVisible(false);
            sell.setVisible(false);
            food.setVisible(false);
            wood.setVisible(false);
        }
        return anchorPane;
    }

    @Override
    public void initializeAnchorPane() {
        anchorPane = new AnchorPane();
        ImageView imageView = new ImageView(ResourceManager.getImage("Market"));
        food = new Button("food");
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
        destroy.setGraphic(imageView);
        destroy.setLayoutX(50);
        destroy.setLayoutY(10);
        wood.setLayoutX(550);
        food.setLayoutX(700);
        food.setLayoutY(60);
        wood.setLayoutY(60);
        buy.setLayoutX(400);
        sell.setLayoutX(550);
        sell.setLayoutY(60);
        buy.setLayoutY(60);
        back.setLayoutX(700);
        back.setLayoutY(60);
        ProgressBar healthBar = new ProgressBar((double) this.health / Settings.MARKET_INITIAL_HEALTH);
        healthBar.setStyle("-fx-accent: #96ff4c;");
        healthBar.setLayoutX(Settings.MENUS_ANCHOR_PANE_WIDTH - 100);
        healthBar.setLayoutY(20);
        healthBar.setPrefSize(100, 20);
        anchorPane.getChildren().addAll(destroy, food, wood, buy, sell, back, healthBar);
        anchorPane.setId("marketMenu");
        anchorPane.setPrefSize(Settings.MENUS_ANCHOR_PANE_WIDTH, Settings.MENUS_ANCHOR_PANE_HEIGHT);
        anchorPane.getStylesheets().add("StrongholdCrusader/css/market.css");
    }

    private void transition(Node button) {
        ScaleTransition transition = new ScaleTransition(Duration.millis(100), button);
        transition.setAutoReverse(true);
        button.setOnMouseEntered(event -> {
            transition.setFromX(1);
            transition.setToX(1.1);
            transition.setFromY(1);
            transition.setToY(1.1);
            transition.play();
        });
        button.setOnMouseExited(event -> {
            transition.setFromX(1.1);
            transition.setToX(1);
            transition.setFromY(1.1);
            transition.setToY(1);
            transition.play();
        });
    }


}
