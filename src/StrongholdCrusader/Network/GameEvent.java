package StrongholdCrusader.Network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Baran on 6/1/2017.
 */
public class GameEvent {
    public static final int JOIN_TO_GAME = 1;
    public static final int USER_JOINED_TO_NETWORK = 2;
    public static final int DUPLICATE_USERNAME = 3;
    public static final int USER_SUCCESSFULLY_CREATED = 4;
    public static final int ALL_PLAYERS = 5;
    public static final int START_GAME = 6;
    public static final int WOOD_CUTTER_CREATED = 7;
    public static final int BARRACKS_CREATED = 8;
    public static final int MARKET_CREATED = 9;
    public static final int PORT_CREATED = 10;
    public static final int QUARRAY_CREATED = 11;
    public static final int FARM_CREATED = 12;
    public static final int MAP_OBJECTS = 13;

    public int type;
    public String message;

    public GameEvent(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public GameEvent() {
    }

    public void show() {
        System.out.println("type: " + type + " , message: " + message);
    }

    public String getJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", this.type);
        jsonObject.put("message", this.message);
        return jsonObject.toJSONString();
    }

    public static GameEvent parseFromString(String string) {
        JSONParser jsonParser = new JSONParser();
        GameEvent gameEvent = null;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(string);
            int type = new Integer(((Long) jsonObject.get("type")).intValue());
            String message = (String) jsonObject.get("message");
            gameEvent = new GameEvent(type, message);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gameEvent;
    }
}
