package net.heinrich_hartmann.titanic;

/**
 * Created by hartmann on 2/9/14.
 */
public class ZmqConfig {

    String transport = "tcp";
    String host = "*";
    String port = "50123";

    String getUrl() {
        return transport + "://" + host + ":" + port;
    }

    public ZmqConfig setTransport(String transport) {
        this.transport = transport;
        return this;
    }

    public ZmqConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public ZmqConfig setPort(String port) {
        this.port = port;
        return this;
    }
}
