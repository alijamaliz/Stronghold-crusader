package StrongholdCrusader;

import javafx.stage.Screen;

/**
 * Created by Baran on 5/29/2017.
 */
public class Settings {
    public static final int SERVER_PORT = 7777;
    public static final int FRAME_RATE = 20;
    public static final int SEND_DATA_RATE = 4;
    public static final int PACKET_MAX_SIZE = 65536;
    public static  int MAP_WIDTH_RESOLUTION = 100;
    public static  int MAP_HEIGHT_RESOLUTION = 100;
    public static int MAP_NAVIGATION_SPEED = 50;
    public static final int MOUSE_MAP_NAVIGATION_MARGIN = 10;
    public static int MAP_TILES_WIDTH = 30;
    public static int MAP_TILES_HEIGHT = 30;
    public static int MENUS_ANCHORPANE_WIDTH = 900;
    public static int MENUS_ANCHORPANE_HEIGHT = 150;
    public static int REFRENCES_ANCHORPANE_WIDTH = 179;
    public static int REFRENCES_ANCHORPANE_HEIGHT = 250;
    public static int ZOOM_AREA_WIDTH = (int) (Screen.getPrimary().getBounds().getWidth()-300);
    public static int ZOOM_AREA_HEIGHT = (int) (Screen.getPrimary().getBounds().getHeight()-300);

    public static int INITIAL_PLAYER_GOLDS = 500;
    public static int INITIAL_PLAYER_WOODS = 200;
    public static int INITIAL_PLAYER_FOODS = 1000;

    public static int SOLDIER_CREATION_NEEDED_GOLD = 20;
    public static int WOOD_CUTTER_CREATION_NEEDED_WOOD = 5;
    public static int BARRACKS_CREATION_NEEDED_WOOD = 20;
    public static int FARM_CREATION_NEEDED_WOOD = 10;
    public static int MARKET_CREATION_NEEDED_WOOD = 10;
    public static int PORT_CREATION_NEEDED_WOOD = 30;
    public static int QUARRY_CREATION_NEEDED_WOOD = 30;
    public static int SHIP_CREATION_NEEDED_WOOD = 50;

    public static int PLAYER_CITY_RADIUS = 35;
    public static int SOLDIER_POWER = 50;
    public static int HUMAN_POWER = 4;
    public static int SOLDIER_ATTACK_RADIUS = 7;
    public static int VASSAL_ATTACK_RADIUS = 3;
    public static int WORKER_ATTACK_RADIUS = 3;
    public static int WORKER_SPEED = 2;
    public static int SOLDIER_SPEED = 4;
    public static int VASSAL_SPEED = 2;
    public static int SOLDIER_INITIAL_HEALTH = 1000;
    public static int WORKER_INITIAL_HEALTH = 500;
    public static int VASSAL_INITIAL_HEALTH = 500;
    public static int BARRACKS_INITIAL_HEALTH = 200;
    public static int FARM_INITIAL_HEALTH = 100;
    public static int MARKET_INITIAL_HEALTH = 100;
    public static int PALACE_INITIAL_HEALTH = 300;
    public static int PORT_INITIAL_HEALTH = 200;
    public static int QUARRY_INITIAL_HEALTH = 100;
    public static int WOOD_CUTTER_INITIAL_HEALTH = 100;
    public static int SHIP_INITIAL_HEALTH = 200;
    public static int SHIP_SPEED = 2;

}
