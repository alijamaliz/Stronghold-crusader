package StrongholdCrusader.GameObjects.Humans;import StrongholdCrusader.Map.MapGUI;import StrongholdCrusader.ResourceManager;import StrongholdCrusader.Settings;import javafx.scene.control.CheckBox;import javafx.scene.control.ProgressBar;import javafx.scene.image.ImageView;import javafx.scene.layout.AnchorPane;/** * Created by Baran on 5/29/2017. */public class Worker extends Human {    public AnchorPane anchorPane;    private CheckBox checkBox;    public Worker() {        this.type = "Worker";        this.speed = Settings.WORKER_SPEED;        this.zone = Settings.WORKER_ATTACK_RADIUS;        this.power = Settings.HUMAN_POWER;        this.health = Settings.WORKER_INITIAL_HEALTH;    }    public Worker(MapGUI mapGUI) {        super(mapGUI);        setImage(ResourceManager.getImage("Worker"));        this.type = "Worker";        this.speed = Settings.WORKER_SPEED;        this.zone = Settings.WORKER_ATTACK_RADIUS;        this.power = Settings.HUMAN_POWER;        this.health = Settings.WORKER_INITIAL_HEALTH;    }    public AnchorPane objectsMenuAnchorPane(boolean owner) {        initializeAnchorPane();        checkBox.setOnAction(event -> mapGUI.changeClimbStatus(Worker.this, checkBox.isSelected()));        if (!owner) {            checkBox.setVisible(false);        }        return anchorPane;    }    @Override    public void initializeAnchorPane() {        anchorPane = new AnchorPane();        ImageView imageView = new ImageView(ResourceManager.getImage("Worker"));        checkBox = new CheckBox("Can Climb ?");        checkBox.setSelected(canClimb);        checkBox.setLayoutX(400);        checkBox.setLayoutY(60);        imageView.setLayoutX(100);        imageView.setLayoutY(40);        ProgressBar healthBar = new ProgressBar((double) this.health / Settings.WORKER_INITIAL_HEALTH);        healthBar.setLayoutX(Settings.MENUS_ANCHOR_PANE_WIDTH - 150);        healthBar.setStyle("-fx-accent: #96ff4c;");        healthBar.setLayoutY(20);        healthBar.setPrefSize(100, 20);        anchorPane.getChildren().addAll(imageView, checkBox, healthBar);        anchorPane.setPrefSize(300, 100);    }}