package com.multicore;

import java.util.Random;


public class ListRunner {

  public void prePopulateList(BasicLinkedList list) {

  }

  public float startThreads(int noOfThreads, BasicLinkedList list, RunMode runMode, int totalOperationsCount) {
      Runner runner = new Runner(noOfThreads, totalOperationsCount);

      // Pre-populate the given list so that read-dominated mode has something to
      // test.
      prePopulateList(list);
      int i = 0;

      Random random = new Random();
      int insertEnd = runMode.percentageOfInserts;
      int deleteStart = insertEnd + 1;
      int deleteEnd = deleteStart + runMode.percentageOfDeletes;

      long startTime = System.currentTimeMillis();
      while (i++ < totalOperationsCount) {
        int randomInt = random.nextInt(100) + 1;
        int key = random.nextInt(RunParameters.MAX_KEY_SIZE.value) + 1;
        if (randomInt >= 1 && randomInt <= insertEnd) {
            runner.run( new BasicThread(i, list, "insert", key) );
        }
        else if (randomInt >= deleteStart && randomInt < deleteEnd) {
            runner.run( new BasicThread(i, list, "delete", key) );
        }
        else {
          runner.run( new BasicThread(i, list, "search", key) );
        }
      }
      runner.waitTillDone();
      runner.shutDown();

      // Validate the list

      long endTime = System.currentTimeMillis();
      float totalTime = (float) (endTime - startTime) / 1000;

      return totalTime;
  }
  public void run(int numberOfThreads, int totalOperationsCount, int runNumber) {

      float executionTime;

      // For each run, execute it for all the different modes.
      for (RunMode mode : RunMode.values()) {

     }
  }
}
