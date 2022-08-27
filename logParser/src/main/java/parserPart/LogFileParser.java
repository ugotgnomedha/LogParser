package parserPart;

import dbPart.InserterDB;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogFileParser {
    private static final Logger logger = LogManager.getLogger(LogFileParser.class);

    public static void logParser(String logFileDir) {
        String level;
        String date;
        String time;
        String thread;
        String logClass;
        String message;

        try (BufferedReader br = new BufferedReader(new FileReader(logFileDir))) {
            String line;
            while ((line = br.readLine()) != null) {

                // Replace all non-visible characters (spaces).
                line = line.replaceAll("\\s+", " ");

                // Determine log's level.
                if (line.contains("INFO")) {
                    level = "INFO";
                } else if (line.contains("ERROR")) {
                    level = "ERROR";
                } else if (line.contains("FATAL")) {
                    level = "FATAL";
                } else if (line.contains("DEBUG")) {
                    level = "DEBUG";
                } else {
                    level = "UNDEFINED";
                }

                // Determine log's other parts.
                if (line.contains(" ")) {
                    line = line.substring(line.indexOf(" ") + 1);
                    date = line.substring(0, line.indexOf(" "));
                    ///
                    line = line.substring(line.indexOf(" ") + 1);
                    time = line.substring(0, line.indexOf(" "));
                    ///
                    line = line.substring(line.indexOf(" ") + 1);
                    thread = line.substring(0, line.indexOf(" ") + 1);
                    ///
                    line = line.substring(line.indexOf(" ") + 1);
                    logClass = line.substring(0, line.indexOf(" ") + 1);
                    ///
                    line = line.substring(line.indexOf(" ") + 3);
                } else {
                    date = "UNDEFINED";
                    time = "UNDEFINED";
                    thread = "UNDEFINED";
                    logClass = "UNDEFINED";
                }
                message = line;

                // Send parsed data to DB.
                InserterDB.logInsert(level, date, time, thread, logClass, message, logFileDir);
            }
        } catch (IOException e) {
            logger.error("Error occurred while parsing .log file.");
        }
    }
}
