package server;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread implements Runnable {

    private final Socket socket;
    private final ServerSocket server;
    private final Database database;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientThread(Socket socket, ServerSocket server, Database database) {
        this.socket = socket;
        this.server = server;
        this.database = database;
        try {
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg = dataInputStream.readUTF();
            JsonObject map = JsonParser.parseString(msg).getAsJsonObject();
            switch (map.get("type").getAsString()) {
                case "set":
                    dataOutputStream.writeUTF(database.set(map.get("key"), map.get("value")));
                    break;
                case "get":
                    dataOutputStream.writeUTF(database.get(map.get("key")));
                    break;
                case "delete":
                    dataOutputStream.writeUTF(database.delete(map.get("key")));
                    break;
                case "exit":
                    JsonObject exitResponse = new JsonObject();
                    exitResponse.addProperty("response", "OK");
                    Gson gson = new Gson();
                    dataOutputStream.writeUTF(gson.toJson(exitResponse));
                    socket.close();
                    server.close();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
