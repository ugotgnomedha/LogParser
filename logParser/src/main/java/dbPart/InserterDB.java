package dbPart;

import java.sql.SQLException;
import java.sql.Statement;

public class InserterDB {
    public static void logInsert(String level, String date, String time, String thread, String logClass, String message, String logFile) {
        try {
            Statement statement = ConnectDB.connection.createStatement();
            String insertSql = "INSERT INTO log_parser(log_file, log_level, log_date, log_time, log_thread, log_class, log_message) " +
                    "VALUES ('" + logFile + "', '" + level + "', '" + date + "', '" + time + "', '" + thread + "', '" + logClass + "', '" + message + "') ON CONFLICT (log_file, log_date, log_time) DO NOTHING";
            statement.executeUpdate(insertSql);
            statement.close();
        } catch (SQLException ignored) {
        }
    }
}
