package server.datainterface;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class JsonDataBase {

    private static JsonDataBase INSTANCE;
    private HashMap<String, String> database = null;

    public static final String databasePath = "../server/data/db.json";
    private final File databaseFile;

    private final Gson gson;

    private final Lock readLock;
    private final Lock writeLock;

    private JsonDataBase() {
        this.databaseFile = new File(databasePath);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
        gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(databaseFile))) {
            database = gson.fromJson(reader, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static JsonDataBase getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (JsonDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JsonDataBase();
                }
            }
        }
        return INSTANCE;
    }

    public JsonMessage get(String key) {
        JsonMessage jsonMessage = new JsonMessage();
        try {
            readLock.lock();
            String value = database.getOrDefault(key, null);
            if (value == null) {
                jsonMessage.setResponse("ERROR");
                jsonMessage.setReason("No such key");
            } else {
                jsonMessage.setResponse("OK");
                jsonMessage.setValue(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return jsonMessage;
    }

    public JsonMessage set(String key, String value) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile))) {
            writeLock.lock();
            database.put(key, value);
            writer.write(gson.toJson(database));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }

        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setResponse("OK");
        return jsonMessage;
    }

    public JsonMessage delete(String key) {
        JsonMessage jsonMessage = new JsonMessage();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseFile))) {
            writeLock.lock();
            String value = database.remove(key);
            if (value != null) {
                writer.write(gson.toJson(database));
                jsonMessage.setResponse("OK");
                return jsonMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }

        jsonMessage.setResponse("ERROR");
        jsonMessage.setReason("No such key");
        return jsonMessage;
    }

}
