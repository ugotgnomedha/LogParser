package starter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        // Get config values.
        String configPath = System.getProperty("log_parser_config");
        ConfigGetter.getConfigs(configPath);
        // Schedule a job.
        try {
            ScheduleTasks.scheduleTask();
        } catch (SchedulerException e) {
            logger.error("Error occurred in ScheduleTasks class.");
        }
    }
}