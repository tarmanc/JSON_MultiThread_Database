package server;

import config.NetworkConfig;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(NetworkConfig.getPort(), 50, NetworkConfig.getHost())) {
            while (!server.isClosed()) {
                try {
                    Socket client = server.accept();
                    if (client != null) {
                        new Thread(new ClientThread(client, server, database)).start();
                    }
                } catch (SocketException e) {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
