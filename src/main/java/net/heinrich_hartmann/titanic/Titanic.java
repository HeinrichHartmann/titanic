package net.heinrich_hartmann.titanic;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Titanic Script
 *
 * Takes in ZMQ messages and stores them in a database.
 *
 * Parameters:
 * - ZMQ Socket options
 * - Database Connection
 * - Batching:
 *   a) no of samples to cache before write
 *   b) time to wait before next write
 *
 */
public class Titanic {
    private static Logger LOG = Logger.getLogger(Titanic.class.getName());
    static {
        org.apache.log4j.BasicConfigurator.configure();
        LOG.setLevel(Level.ERROR);
    }

    private static int FLUSH_COUNT = 100;

    public static void main(String[] argv) throws SQLException {

        DbConfig dbConfig = new DbConfig()
                .setDbName("titanic")
                .setUser("titanic")
                .setPass("titanic");

        ZmqConfig zmqConfig = new ZmqConfig()
                .setPort("50123");

        try (
            DbHolder dbHolder = new DbHolder(dbConfig);
            ZmqHolder zmqHolder = new ZmqHolder(zmqConfig)
        ) {
            long msgcount = 0;
            while (true) {
                try {
                    String message = zmqHolder.recv();

                    dbHolder.queueMessage(message);

                    if (++msgcount % FLUSH_COUNT == 0) {
                        dbHolder.flush();
                    }

                } catch (InterruptedException e){
                    LOG.info("Interrupted Message receiving", e);
                    break;
                }
            }
        } catch (SQLException | ZmqHolder.ZmqException e) {
            LOG.error(e);
        }
    }
}
