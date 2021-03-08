package server;

import com.google.gson.*;

import java.io.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    private final JsonObject db = new JsonObject();
    private final JSONProcessor processor;
    private final String filePath = System.getProperty("user.dir") + "/src/server/data/db.json";
    final String ERROR = "No such key";
    private final Lock writeLock;
    private final Lock readLock;
    //Implement readLock//

    public Database() {
        this.processor = new JSONProcessor();

        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    private void saveDatabase() {
        try {
            writeLock.lock();
            FileWriter writer = new FileWriter(filePath);
            writer.write(new Gson().toJson(db));
            writer.close();
            writeLock.unlock();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String set(JsonElement pos, JsonElement value) {
        JsonObject data = db;
        if (pos.isJsonArray()) {

            JsonArray path = pos.getAsJsonArray();

            for (int i = 0; i < path.size(); i++) {
                if (i == path.size() - 1) {
                    data.add(path.get(i).getAsString(), value);
                    break;
                } else if (data.has(path.get(i).getAsString())) {
                    if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                        data.add(path.get(i).getAsString(), new JsonObject());
                    }
                } else {
                    data.add(path.get(i).getAsString(), new JsonObject());
                }
                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            db.add(pos.getAsString(), value);
        }

        saveDatabase();
        return processor.getSuccess();
    }


    public String get(JsonElement pos) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
            if (pos.isJsonArray()) {
                JsonArray path = pos.getAsJsonArray();
                if (path.size() == 1) {
                    if (!data.has(pos.getAsString())) {
                        return processor.getError(ERROR);
                    } else {
                        saveDatabase();
                        return processor.getValue(data.get(path.get(0).getAsString()).getAsJsonObject());
                    }
                } else {
                    for (int i = 0; i < path.size(); i++) {
                        if (data.has(path.get(i).getAsString())) {
                            if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                                if (i == path.size() - 1) {
                                    return processor.getValue(data.get(path.get(i).getAsString()).getAsString());
                                } else {
                                    return processor.getError(ERROR);
                                }
                            } else {
                                data = data.get(path.get(i).getAsString()).getAsJsonObject();
                            }
                        } else {
                            return processor.getError(ERROR);
                        }
                    }
                }
            } else {
                if (!data.has(pos.getAsString())) {
                    return processor.getError(ERROR);
                } else {
                    return processor.getValue(new Gson().toJson(data.get(pos.getAsString())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processor.getError(ERROR);
    }

    public String delete(JsonElement pos) {
        JsonObject data = db;
        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();
            for (int i = 0; i < path.size(); i++) {
                if (!data.has(path.get(i).getAsString())) {
                    return processor.getError(ERROR);
                } else if (i == path.size() - 1) {
                    if (data.has(path.get(i).getAsString())) {
                        data.remove(path.get(i).getAsString());
                        break;
                    }
                } else if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                    return processor.getError(ERROR);
                }
                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            if (!data.has(pos.getAsString())) {
                return processor.getError(ERROR);
            } else {
                db.remove(pos.getAsString());
            }
        }
        saveDatabase();
        return processor.getSuccess();
    }
}