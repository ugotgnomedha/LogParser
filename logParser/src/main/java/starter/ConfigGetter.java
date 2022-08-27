package starter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigGetter {
    public static String dbUrl = "";
    public static String dbUser = "";
    public static String dbPass = "";
    public static String logFileDir = "";
    public static String parsePeriod = "";
    public static String cleanDBPeriod = "";

    public static void getConfigs(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            Properties prop = new Properties();
            prop.load(fis);

            ///
            dbUrl = prop.getProperty("DBurl");
            dbUser = prop.getProperty("DBuser");
            dbPass = prop.getProperty("DBpassword");
            ///
            logFileDir = prop.getProperty("logFileDirectory");
            parsePeriod = prop.getProperty("parsePeriodSeconds");
            cleanDBPeriod = prop.getProperty("cleanPeriodHours");
            ///

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
