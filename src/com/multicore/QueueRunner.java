package com.multicore;

import java.util.Random;


public class QueueRunner {


    public float startThreads(int noOfThreads, UnBoundedQueue queue, RunMode runMode, int totalOperationsCount) {
        Utils.logInfo("Init runner with noOfThreads: " + noOfThreads + " totalOpscount: " + totalOperationsCount);
        Runner runner = new Runner(noOfThreads, totalOperationsCount);

        int i = 1;

        Random random = new Random();
        int enqueueEnd = runMode.percentageOfEnqueue;
        int dequeueStart = enqueueEnd + 1;
        int dequeueEnd = dequeueStart + runMode.percentageOfDequeue;

        long startTime = System.currentTimeMillis();
        while (i++ < totalOperationsCount) {
            int randomInt = random.nextInt(100) + 1;
            int key = random.nextInt(RunParameters.MAX_KEY_SIZE.value) + 1;
            if (randomInt >= 1 && randomInt <= enqueueEnd) {
                runner.run( new BasicThread(i, queue, "enq", key) );
            }
            else if (randomInt >= dequeueStart && randomInt < dequeueEnd) {
                runner.run( new BasicThread(i, queue, "deq", key) );
            }
            else {
                runner.run( new BasicThread(i, queue, "isEmpty", key) );
            }
        }

        runner.waitTillDone();
        runner.shutDown();

        long endTime = System.currentTimeMillis();
        float totalTime = (float) (endTime - startTime) / 1000;

        return totalTime;
    }

    public void run(int numberOfThreads, int totalOperationsCount, int runNumber) {

        float executionTime;

        // For each run, execute it for all the different modes.
        for (RunMode mode : RunMode.values()) {

            Utils.logInfo("Run #:" + runNumber + " Lockbased Queue, Mode: " + mode.modeName);
            UnBoundedQueue lockBasedQueue = new UnboundedLockBasedQueue();
            executionTime = startThreads(numberOfThreads, lockBasedQueue, mode, totalOperationsCount);
            Utils.logInfo("Run #:" + runNumber + " Number of Threads: " + numberOfThreads + " Queue: LockBased, Mode: " + mode.modeName + " Execution Time: " + executionTime + " seconds.");

            Utils.logInfo("Run #:" + runNumber + " LockFree Queue, Mode: " + mode.modeName);
            UnBoundedQueue lockFreeQueue = new UnBoundedLockFreeQueue();
            executionTime = startThreads(numberOfThreads, lockFreeQueue, mode, totalOperationsCount);
            Utils.logInfo("Run #:" + runNumber + " Number of Threads: " + numberOfThreads + " Queue: LockFree, Mode: " + mode.modeName + " Execution Time: " + executionTime + " seconds.");

        }
    }
}
