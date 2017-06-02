package StrongholdCrusader.Network;

/**
 * Created by Baran on 6/1/2017.
 */
public class GameEvent {
    public static final int JOIN_TO_GAME = 1;

    int type;
    String message;

    public void show() {
        System.out.println("type: " + type + " , message: " + message);
    }
}
