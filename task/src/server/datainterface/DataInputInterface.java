package server.datainterface;

import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class DataInputInterface implements Runnable {

    private final ServerSocket server;
    private final JsonMessage incomingMessage;

    public DataInputInterface(ServerSocket server, JsonMessage incomingMessage) {
        this.server = server;
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void run() {
        Gson gson = new Gson();

        try(Socket socket = server.accept();
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            outputStream.writeUTF(gson.toJson(inputInterface()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonMessage inputInterface() {
        JsonMessage response = new JsonMessage();
        switch (incomingMessage.getType()) {
            case "get":
                response = JsonDataBase.getINSTANCE().get(incomingMessage.getKey());
                break;
            case "set":
                response = JsonDataBase.getINSTANCE().set(incomingMessage.getKey(), incomingMessage.getValue());
                break;
            case "delete":
                response = JsonDataBase.getINSTANCE().delete(incomingMessage.getKey());
                break;
        }
        return response;
    }


}
