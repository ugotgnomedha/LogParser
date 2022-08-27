package parserPart;

public class LogFileThreads {
    public static class sendThreadsToParse implements Runnable {
        String fileDir;

        public sendThreadsToParse(String fileDir) {
            this.fileDir = fileDir;
        }

        @Override
        public void run() {
            LogFileParser.logParser(fileDir);
        }
    }

    public static void createFileThreads(String fileDir) {
        Thread t = new Thread(new sendThreadsToParse(fileDir));
        t.start();
    }
}
