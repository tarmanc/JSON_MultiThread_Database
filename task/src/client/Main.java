package client;

import client.argscommander.Args;
import com.beust.jcommander.JCommander;
import config.NetworkConfig;
import server.datainterface.JsonMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Args inputArgs = new Args();
        JCommander.newBuilder()
                .addObject(inputArgs)
                .build()
                .parse(args);
        connectionTest(messageBuild(inputArgs));
    }

    private static String messageBuild(Args args) {
        JsonMessage jsonMessage = new JsonMessage();
        if (args.getFileName() != null) {
            String path = "../src/client/data/" + args.getFileName();
            File file = new File(path);
            try (Scanner scanner = new Scanner(file)) {
                return scanner.nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonMessage.setType(args.getCommand());
        jsonMessage.setKey(args.getKey());
        jsonMessage.setValue(args.getMessage());
        return jsonMessage.getJsonMessage();
    }

    private static void connectionTest(String message) {
        try (Socket socket = new Socket(NetworkConfig.getHost(), NetworkConfig.getPort());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

            System.out.println("Sent: " + message);
            outputStream.writeUTF(message);

            System.out.println("Received: " + inputStream.readUTF());
        } catch (IOException e) {
            try {
                Thread.sleep(1000);
                connectionTest(message);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }


}
