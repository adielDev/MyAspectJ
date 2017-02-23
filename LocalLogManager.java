package adiel.myaspectj;

import android.util.Log;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


/**
 * Created by recntrek7 on 08/01/17.
 */

public  class LocalLogManager {

    // cd data/data/com.recntrek.superLogger/files
    //static Logger logger =Logger.getLogger("recntrek.localLogger.icl");
    static final String LOGGER_NAME = "recntrek.superLogger";
    private static final int MAX_FILE_SIZE_IN_BYTES = 10000;
    private static final int NUM_OF_FILES = 1;
    private static Logger mLoggerInstance=null;
    private static ExecutorService threadPool;

    public String key ="key";


    public synchronized static void initLogger() throws IOException {
        LogManager lManager = LogManager.getLogManager();
        if(lManager!=null) {
                threadPool = Executors.newFixedThreadPool(1);
                FileHandler fileHandler = new FileHandler("/data/data/com.recntrek.superLogger/files/localLogger.log",MAX_FILE_SIZE_IN_BYTES,NUM_OF_FILES);
                //fileHandler.setFormatter(new SimpleFormatter());
                fileHandler.setFormatter(new SuperLoggerFormatter());
                fileHandler.setLevel(Level.ALL);
                ConsoleHandler consoleHandler = new ConsoleHandler();
                mLoggerInstance = Logger.getLogger(LOGGER_NAME);
                mLoggerInstance.setUseParentHandlers(false);
                if(mLoggerInstance!=null) {
                    mLoggerInstance.addHandler(fileHandler);
                    mLoggerInstance.addHandler(consoleHandler);
                    mLoggerInstance.setLevel(Level.ALL);
                    lManager.addLogger(mLoggerInstance);
                    Log.d("adiel","locaLogmManager add to manager !!!!");
                    superLog("init");
                }else{
                    MyLoggerNotCreateException myLoggerNotCreateException = new MyLoggerNotCreateException();
                    myLoggerNotCreateException.printStackTrace();
                }




        }

    }

    public static synchronized void superLog(String msg){
        if(mLoggerInstance==null){
         //   selfInit();
            //InstanceIsNullException instanceIsNullException = new InstanceIsNullException();
            //instanceIsNullException.printStackTrace();
        }else {
            LogWriter logWriter = new LogWriter(msg);
            threadPool.submit(logWriter);
        }

    }


    private static class LogWriter implements Runnable {
        String msg;
        public LogWriter(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            mLoggerInstance.log(Level.FINE, msg);
        }

    }

    private static class IclFormatter extends Formatter {

        @Override
        public synchronized String format(LogRecord logRecord) {
            String message = logRecord.getMessage();
            long millis = logRecord.getMillis();
            Date date = new Date(millis);
            String retVal = String.format("%s : %n %s%n",date,message);
            return retVal;
        }
    }

    private static class SuperLoggerFormatter extends Formatter {

        @Override
        public synchronized String format(LogRecord logRecord) {
            return logRecord.getMessage();
        }
    }

    private static class InstanceIsNullException extends RuntimeException {
        @Override
        public String getMessage() {
            return "instance is needed to init inside onCreate of application !!!";
        }
    }
    private static class MyLoggerNotCreateException extends RuntimeException {
        @Override
        public String getMessage() {
            return "mLoggerInstance = Logger.getLogger(LOGGER_NAME); return null";
        }
    }
}
           /*try {
                lManager.readConfiguration(loggingProperties);
            } catch (IOException e) {
                e.printStackTrace();
            }
*/