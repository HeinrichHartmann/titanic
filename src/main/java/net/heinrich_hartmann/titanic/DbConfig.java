package net.heinrich_hartmann.titanic;

/**
 * Created by hartmann on 2/9/14.
 */
public class DbConfig {

    // Package local values
    String host = "127.0.0.1";
    String port = "5432";
    String dbName = "";
    String user = "";
    String pass = "";

    public DbConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public DbConfig setPort(String port) {
        this.port = port;
        return this;
    }

    public DbConfig setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public DbConfig setUser(String user) {
        this.user = user;
        return this;
    }

    public DbConfig setPass(String pass) {
        this.pass = pass;
        return this;
    }

    String getUrl() { return "jdbc:postgresql://"+ host + ":" + port +"/"+ dbName; }
}
