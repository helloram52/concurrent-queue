package com.multicore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class QueueRunner {

    public float startThreads(int noOfThreads, UnBoundedQueue queue, RunMode runMode, int totalOperationsCount) {
        Runner runner = new Runner(noOfThreads, totalOperationsCount);

        int i = 0;

        Random random = new Random();
        int enqueueEnd = runMode.percentageOfEnqueue;
        int dequeueStart = enqueueEnd + 1;
        int dequeueEnd = dequeueStart + runMode.percentageOfDequeue;
        ArrayList<Integer> threadCounter = new ArrayList<>(Collections.nCopies(noOfThreads+1, 0));
        ArrayList<Integer> lastDequeued = new ArrayList<>(Collections.nCopies(noOfThreads+1, 0));

        long startTime = System.currentTimeMillis();

        while (i++ < totalOperationsCount) {
            int randomInt = random.nextInt(100) + 1;

            if (randomInt >= 1 && randomInt <= enqueueEnd) {
                if (Utils.testFlag) {
                    runner.run( new BasicThread(i, queue, "enq", -1, threadCounter, lastDequeued) );
                }
                else {

                    int key = random.nextInt(RunParameters.MAX_KEY_SIZE.value) + 1;
                    runner.run( new BasicThread(i, queue, "enq", key, threadCounter, lastDequeued) );
                }
            }
            else if (randomInt >= dequeueStart && randomInt < dequeueEnd) {
                runner.run( new BasicThread(i, queue, "deq", threadCounter, lastDequeued) );
            }
            else {
                runner.run( new BasicThread(i, queue, "isEmpty", threadCounter, lastDequeued) );
            }
        }

        runner.waitTillDone();
//        Utils.logInfo("after tilldone");
        runner.shutDown();
//        Utils.logInfo("after shutdown");

        long endTime = System.currentTimeMillis();
        float totalTime = (float) (endTime - startTime) / 1000;

        return totalTime;
    }

    public void run(int numberOfThreads, int totalOperationsCount, int runNumber) {

        float executionTime;

        // For each run, execute it for all the different modes.
        for (RunMode mode : RunMode.values()) {

//            Utils.logInfo("Run #:" + runNumber + " Lockbased Queue, Mode: " + mode.modeName);
            UnBoundedQueue lockBasedQueue;
            if (Utils.testFlag) {
                lockBasedQueue = new UnboundedLockBasedQueue<TestQueueItem>();
            }
            else {
                lockBasedQueue = new UnboundedLockBasedQueue<Integer>();
            }
            executionTime = startThreads(numberOfThreads, lockBasedQueue, mode, totalOperationsCount);
            Utils.logInfo("Run #:" + runNumber + " Number of Threads: " + numberOfThreads + " Queue: LockBased, Mode: " + mode.modeName + " Execution Time: " + executionTime + " seconds.");

//            Utils.logInfo("Run #:" + runNumber + " LockFree Queue, Mode: " + mode.modeName);
            UnBoundedQueue lockFreeQueue;
            if (Utils.testFlag) {
                lockFreeQueue = new UnBoundedLockFreeQueue<TestQueueItem>();
            }
            else {
                lockFreeQueue = new UnBoundedLockFreeQueue<Integer>();
            }

            executionTime = startThreads(numberOfThreads, lockFreeQueue, mode, totalOperationsCount);
            Utils.logInfo("Run #:" + runNumber + " Number of Threads: " + numberOfThreads + " Queue: LockFree, Mode: " + mode.modeName + " Execution Time: " + executionTime + " seconds.");

        }
    }
}
