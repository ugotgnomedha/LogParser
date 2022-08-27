package starter;

import dbPart.ConnectDB;
import dbPart.TableCleanerDB;
import dbPart.TableCreatorDB;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import parserPart.LogFileFinder;
import parserPart.LogFileThreads;

import java.io.File;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleTasks {
    private static final Logger logger = LogManager.getLogger(ScheduleTasks.class);

    public static void scheduleTask() throws SchedulerException {
        // Schedule to run at the exact given day/hour/minute/second.
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // Create a Parser job.
        JobDetail jobParser = newJob(runner.class)
                .withIdentity("log_parser")
                .build();

        //Schedule to run every config given seconds.
        Trigger triggerParser = newTrigger().withIdentity("triggerParse")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(ConfigGetter.parsePeriod))
                        .repeatForever())
                .build();

        //////////////

        // Create a Cleaner job.
        JobDetail jobClean = newJob(cleaner.class)
                .withIdentity("log_parser_cleaner")
                .build();

        Trigger triggerCleaner = newTrigger().withIdentity("triggerClean")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(Integer.parseInt(ConfigGetter.cleanDBPeriod))
                        .repeatForever())
                .build();

        scheduler.scheduleJob(jobParser, triggerParser);
        logger.info("Parser task set.");
        scheduler.scheduleJob(jobClean, triggerCleaner);
        logger.info("Cleaner task set.");
    }

    public static class runner implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            // Start parsing .log files if connection to db exists.
            if (ConnectDB.connectToDB()) {
                // Check if table exists.
                TableCreatorDB.createTable();

                // Start parsing.
                File[] logFiles = LogFileFinder.fileFinder();
                for (File logFile : logFiles) {
                    LogFileThreads.createFileThreads(logFile.toString());
                }
            }
        }
    }

    public static class cleaner implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            // Clean table.
            TableCleanerDB.cleanTable();
        }
    }
}
