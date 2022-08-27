package parserPart;

import starter.ConfigGetter;

import java.io.File;

public class LogFileFinder {
    public static File[] fileFinder() {
        // Find .log files in config directory.
        File f = new File(ConfigGetter.logFileDir);
        return f.listFiles((dir, name) -> name.endsWith("log"));
    }
}
