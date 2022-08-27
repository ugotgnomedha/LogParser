package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starter.ConfigGetter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCleanerDB {
    private static final Logger logger = LogManager.getLogger(TableCleanerDB.class);
    public static void cleanTable() {
        try {
            Connection connection = DriverManager.getConnection(ConfigGetter.dbUrl, ConfigGetter.dbUser, ConfigGetter.dbPass);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM log_parser");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error("Error occurred while trying to clean log_parser table.");
        }
    }
}
