package client.argscommander;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Args {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-t", description = "get/set/delete commands")
    private String command;

    @Parameter(names = "-k", description = "database key")
    private String key;

    @Parameter(names = "-v", description = "payload")
    private String message;

    @Parameter(names = "-in", description = "jsonFilePath")
    private String fileName;

    public String getCommand() {
        return command;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getFileName() {
        return fileName;
    }
}
