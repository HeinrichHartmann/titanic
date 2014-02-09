package net.heinrich_hartmann.titanic;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by hartmann on 2/9/14.
 */
public class DbHolder implements AutoCloseable {
    private static Logger LOG = Logger.getLogger(DbHolder.class.getName());

    private Connection connection;
    PreparedStatement insertStatement;

    public DbHolder(DbConfig cfg) throws SQLException {
        connection = DriverManager.getConnection(cfg.getUrl(), cfg.user, cfg.pass);
        insertStatement = connection.prepareStatement("INSERT INTO messages(timestamp, message) VALUES (?,?)");

        createMessageTable();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }

    /**
     * Creates table to store messages
     * @throws SQLException
     */
    public void createMessageTable() throws SQLException {
        try (Statement stmt = connection.createStatement()){
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS messages(" +
                        "id BIGSERIAL PRIMARY KEY," +
                        "timestamp BIGINT," +
                        "message TEXT);"
            );
            LOG.info("Created Table");
        }
    }

    public void queueMessage(String message) throws SQLException {
        LOG.info("Adding message " + message);
        insertStatement.setDouble(1, System.currentTimeMillis());
        insertStatement.setString(2, message);
        insertStatement.addBatch();
    }

    public void flush() throws SQLException {
        LOG.info("Flushing to db.");
        insertStatement.executeBatch();
    }
}
