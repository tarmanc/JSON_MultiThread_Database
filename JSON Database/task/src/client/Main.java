package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import config.NetworkConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        new MainClass().main(args);
    }
}

class Args {
    @Parameter(names = {"--type", "-t"})
    String command;
    @Parameter(names = {"--key", "-k"})
    String key;
    @Parameter(names = {"--value", "-v"})
    String value;
    @Parameter(names = {"--input", "-in"})
    String filename;
}

class MainClass {
    void main(String[] args) {
        System.out.println("Client started!");
        try (Socket socket = new Socket(NetworkConfig.getHost(), NetworkConfig.getPort());
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            String[] testArguments;
            if (args[0].equals("Main")) {
                testArguments = Arrays.copyOfRange(args, 1, args.length);
            } else {
                testArguments = args.clone();
            }

            Args arguments = new Args();
            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(testArguments);

            String result = "";
            if (arguments.filename == null) {
                Map<String, String> json = new LinkedHashMap<>();
                json.put("type", arguments.command);
                if (arguments.key != null) {
                    json.put("key", arguments.key);
                }
                if (arguments.value != null) {
                    json.put("value", arguments.value);
                }
                result = new Gson().toJson(json);
            } else {
                Path filepath = Paths.get(System.getProperty("user.dir") + "/src/client/data/" + arguments.filename);
                result = String.join("\n", Files.readAllLines(filepath));

            }

            System.out.println("Sent: " + result);
            output.writeUTF(result);

            String res = input.readUTF();
            System.out.println("Received: " + res);
        } catch (IOException e) {
            System.out.println("Error while reading file");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}