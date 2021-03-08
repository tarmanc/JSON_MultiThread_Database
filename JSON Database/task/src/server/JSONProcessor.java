package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONProcessor {
    private final Gson gson = new Gson();

    public String getError(String reason) {
        Map<String, String> json = new LinkedHashMap<>();
        json.put("response", "ERROR");
        json.put("reason", reason);
        return gson.toJson(json);
    }

    public String getSuccess() {
        Map<String, String> json = new HashMap<>();
        json.put("response", "OK");
        return gson.toJson(json);
    }

    public String getValue(String value) {
        Map<String, String> json = new LinkedHashMap<>();
        json.put("response", "OK");
        json.put("value", value);
        return gson.toJson(json);
    }

    public String getValue(JsonObject value) {
        Map<String, Object> json = new LinkedHashMap<>();
        json.put("response", "OK");
        json.put("value", value);
        return gson.toJson(json);
    }
}
