package com.multicore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public final class Utils {

    public static boolean debugFlag = true;
    private static Logger logger;
    private static HashMap<Integer, ArrayList<Float>> statsMap;

  public Utils() {
    statsMap = new HashMap<>();
  }

  public static void setLogger() {
    logger = Logger.getLogger("CustomLog");
    logger.setUseParentHandlers(false);

    FileHandler fh;

    try {
      fh = new FileHandler("run.log");
      logger.addHandler(fh);
      SimpleFormatter formatter = new SimpleFormatter();
      fh.setFormatter(new Formatter() {
        @Override
        public String format(LogRecord record) {
          SimpleDateFormat logTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
          Calendar cal = new GregorianCalendar();
          cal.setTimeInMillis(record.getMillis());

          return "[" + logTime.format(cal.getTime())
            + "] Nanotime: " + System.nanoTime()
            + " " + record.getLevel()
            + " : "
            + record.getMessage() + "\n";
        }
      });
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param msg
   */
  public static void Debug(String msg) {
    if (debugFlag)
      System.out.println("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS").format(new Date()) + "] " + msg);
  }

  public static void logInfo(String message) {
      logger.info(message);
  }

  public static void logWarning(String message) {
    logger.warning(message);
  }

  public static void gatherStatistics(int algorithmIndex, float executionTime) {
    ArrayList<Float> temp = statsMap.get(algorithmIndex);
    if(temp != null) {
      temp.add(executionTime);
      statsMap.put(algorithmIndex, temp);
    }
    else {
      ArrayList<Float> floatArrayList = new ArrayList<>(1);
      floatArrayList.add(executionTime);
      statsMap.put(algorithmIndex, floatArrayList);
    }
  }

  public static void printStatistics() {

  }
}

