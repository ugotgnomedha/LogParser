package dbPart;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreatorDB {
    private static final Logger logger = LogManager.getLogger(TableCreatorDB.class);

    public static void createTable() {
        Statement statement;
        try {
            ResultSet table = null;
            try {
                DatabaseMetaData dbm = ConnectDB.connection.getMetaData();
                table = dbm.getTables(null, null, "log_parser", null);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            if (!table.next()) {
                statement = ConnectDB.connection.createStatement();
                statement.executeUpdate("CREATE TABLE log_parser(id SERIAL UNIQUE, log_file VARCHAR, log_level VARCHAR, " +
                        "log_date VARCHAR, log_time VARCHAR, log_thread VARCHAR, log_class VARCHAR, log_message VARCHAR, PRIMARY KEY(log_file, log_date, log_time))");
                statement.close();
            }
        } catch (Exception exception) {
            logger.error("Could not add column to a database table.");
            exception.printStackTrace();
        }
    }
}
