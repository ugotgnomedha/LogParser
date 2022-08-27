package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import starter.ConfigGetter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static final Logger logger = LogManager.getLogger(ConnectDB.class);
    public static Connection connection = null;

    public static Boolean connectToDB() {
        boolean flag = false;
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(ConfigGetter.dbUrl, ConfigGetter.dbUser, ConfigGetter.dbPass);
            flag = !connection.isClosed();
        } catch (SQLException ex) {
            logger.error("Error occurred while connecting to database.");
        }
        return flag;
    }
}
