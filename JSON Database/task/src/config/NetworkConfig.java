package config;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkConfig {

    private static final String host = "127.0.0.1";
    private static final int port = 12345;

    public static InetAddress getHost() throws UnknownHostException {
        return InetAddress.getByName(host);
    }

    public static int getPort() {
        return port;
    }
}

