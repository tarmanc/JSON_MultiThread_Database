package server.datainterface;

import com.google.gson.Gson;


public class JsonMessage {

    private String response;
    private String reason;
    private String value;
    private String type;
    private String key;

    public String getJsonMessage() {
        return new Gson().toJson(this);
    }

    public String getType() {
        return type;
    }


    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
