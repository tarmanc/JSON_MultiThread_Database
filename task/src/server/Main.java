package server;

import com.google.gson.Gson;
import config.NetworkConfig;
import server.datainterface.DataInputInterface;
import server.datainterface.JsonMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Server started!");

        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket server = new ServerSocket(NetworkConfig.getPort(), 50, NetworkConfig.getHost());
            while (true) {
                    Socket socket = server.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                    Gson gson = new Gson();
                    JsonMessage input = gson.fromJson(inputStream.readUTF(), JsonMessage.class);

                    if (input.getType().equals("exit")) {
                        JsonMessage response = new JsonMessage();
                        response.setResponse("OK");
                        outputStream.writeUTF(response.getJsonMessage());
                        executorService.shutdown();
                        executorService.awaitTermination(60, TimeUnit.MILLISECONDS);
                        break;
                    } else {
                        executorService.execute(new DataInputInterface(server,input));
                    }

                    socket.close();
                    inputStream.close();
                    outputStream.close();
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
