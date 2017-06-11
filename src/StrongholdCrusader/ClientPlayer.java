package StrongholdCrusader;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Network.Client;
import StrongholdCrusader.Network.GameEvent;

/**
 * Created by Baran on 6/3/2017.
 */
public class ClientPlayer {
    String username;
    Client client;
    Map map;
    MenuGUI menuGUI;

    public ClientPlayer(String username, String serverIP, MenuGUI menuGUI) {
        this.username = username;
        client = new Client(serverIP);
        client.sendJoinRequest(username);
        map = new Map();
        this.menuGUI = menuGUI;

        Runnable gameEventHandleRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (client.hasNewEvent()) {
                        analyseGameEvent(client.getEvent());
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(gameEventHandleRunnable).start();
    }


    private void analyseGameEvent(GameEvent gameEvent) {
        switch (gameEvent.type) {
            case GameEvent.USER_JOINED_TO_NETWORK: {
                String username = gameEvent.message.substring(0, gameEvent.message.indexOf(','));
                String address = gameEvent.message.substring(gameEvent.message.indexOf(",") + 1);
                menuGUI.addPlayerToTable(username, address);
                break;
            }
            case GameEvent.START_GAME: {
                System.out.println("Start");
                map.showMapScreen();
            }
        }
    }
}
