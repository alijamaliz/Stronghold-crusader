package StrongholdCrusader.Network;

import StrongholdCrusader.Map.Map;
import StrongholdCrusader.Settings;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.*;

/**
 * Created by Baran on 6/2/2017.
 */
public class Client implements Runnable {
    DatagramSocket socket;
    InetAddress serverAddress;
    String lastServerMap;

    public Client(String serverIP) {
        lastServerMap = "";
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(serverIP);
            serverAddress = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[Settings.PACKET_MAX_SIZE];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            while (true) {
                socket.receive(incoming);
                byte[] data = incoming.getData();
                lastServerMap = new String(data, 0, incoming.getLength());
                System.out.println(lastServerMap);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
        }
    }

    public void sendJoinRequest(String playerName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", GameEvent.JOIN_TO_GAME);
        jsonObject.put("message", playerName);
        System.out.println(sendPacket(jsonObject.toJSONString(), serverAddress, Settings.SERVER_PORT));
    }

    private String sendPacket(String body, InetAddress address, int port) {
        DatagramPacket dp = new DatagramPacket(body.getBytes(), body.getBytes().length, address, port);
        try {
            socket.send(dp);

            byte[] buffer = new byte[Settings.PACKET_MAX_SIZE];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            socket.receive(reply);
            byte[] response = reply.getData();
            return new String(response);
        } catch (IOException e) {
            e.printStackTrace();
            return "Fail";
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1");
        client.sendJoinRequest("Ali");
        new Thread(client).start();
        while (true) {

        }
    }

    public Map getLastServerMap() {
        Map lastMap = new Map();
        //TODO parse map from string
        return lastMap;
    }
}
