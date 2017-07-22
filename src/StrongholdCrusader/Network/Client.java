package StrongholdCrusader.Network;

import StrongholdCrusader.Settings;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * Created by Baran on 6/2/2017.
 */
public class Client implements Runnable {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private Queue<GameEvent> events;
    private Semaphore semaphore;

    public Client(String serverIP) {
        events = new LinkedList<>();
        semaphore = new Semaphore(1);
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            //e.printStackTrace();
        }
        try {
            serverAddress = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[Settings.PACKET_MAX_SIZE];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(incoming);
                byte[] data = incoming.getData();
                GameEvent gameEvent = GameEvent.parseFromString(new String(data, 0, incoming.getLength()));
                semaphore.acquire();
                events.add(gameEvent);
                semaphore.release();
                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
    }

    public boolean hasNewEvent() {
        boolean res = false;
        try {
            res = events.size() != 0;
        } catch (Exception ignored) {
        }
        return res;
    }

    public GameEvent getEvent() {
        GameEvent gameEvent = null;
        try {
            gameEvent = events.poll();
        } catch (Exception ignored) {
        }
        return gameEvent;
    }

    public void sendJoinRequest(String playerName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", GameEvent.JOIN_TO_GAME);
        jsonObject.put("message", playerName);
        sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }

    public void sendGetAllPlayersRequest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", GameEvent.ALL_PLAYERS);
        jsonObject.put("message", "");
        sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }

    public void sendGameEvent(int type, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("message", message);
        sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT);
    }

    private void sendPacket(String body, InetAddress address, int port) {
        DatagramPacket dp = new DatagramPacket(body.getBytes(), body.getBytes().length, address, port);
        try {
            socket.send(dp);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}
