package com.multicore;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicThread implements Runnable {

  private int operationID;
  private String operationName;
  private int key;
  private UnBoundedQueue queue;
  private ArrayList<Integer> threadCounter;
  private ArrayList<Integer> lastDequeued;

  public int getOperationID() {
    return operationID;
  }

  public BasicThread(int operationID, UnBoundedQueue queue, String operationName, int key,
    ArrayList<Integer> threadCounter, ArrayList<Integer> lastDequeued) {
    this.operationID = operationID;
    this.queue = queue;
    this.operationName = operationName;
    this.key = key;
    this.threadCounter = threadCounter;
    this.lastDequeued = lastDequeued;
  }

  // Constructor for dequeue/isEmpty operations that don't need a key.
  public BasicThread(int operationID, UnBoundedQueue queue, String operationName,
    ArrayList<Integer> threadCounter, ArrayList<Integer> lastDequeued) {
    this(operationID, queue, operationName, -1, threadCounter, lastDequeued);
  }

  public void run() {

    int value = 0;
    TestQueueItem item = null;
    TestQueueItem v = null;
    String threadname = Thread.currentThread().getName();

    Pattern p = Pattern.compile("pool-\\d+-thread-(\\d+)");
    Matcher m = p.matcher(threadname);
    m.find();
    int threadID = Integer.valueOf( m.group(1) );
//    Utils.logInfo("threadname: " + threadname + " threadid: " + threadID);

    boolean result;


    switch (operationName) {
      case "enq":

//        Utils.logInfo(threadname + " : Invoking OperationID: " + operationID );
        if (Utils.testFlag) {
          int counterValue = threadCounter.get(threadID) + 1;
          item = new TestQueueItem(threadID, counterValue);
          threadCounter.set(threadID, counterValue);

          queue.enq(item);

//          Utils.logInfo(threadname + " : OperationID: " + operationID +  " Enq(" + item + ")");
        }
        else {
          queue.enq(key);
//          Utils.logInfo(threadname + " : OperationID: " + operationID +  " Enq(" + key + ")");
        }
        break;

      case "deq":

//        Utils.logInfo(threadname + " : Invoking OperationID: " + operationID );
        try {
          if (Utils.testFlag) {
            v = (TestQueueItem) queue.deq();

            int lastDequeuedValue = lastDequeued.get( v.getThreadID() );
            int expectedValue = lastDequeuedValue + 1;

            if (v.getValue() != expectedValue) {
              Utils.logError("[Mismatch] Expected Value: " + expectedValue + " actual value: " + v.getValue() + " for threadID: " + v.getThreadID());
            }

            lastDequeued.set(v.getThreadID(), v.getValue());
          }
          else {
            value = (int) queue.deq();
          }
        }
        catch (Exception e) {
//          Utils.logInfo("Exception e: " + e.getStackTrace());
          value = -1;
          v = null;
        }

        if (Utils.testFlag) {
//          Utils.logInfo(threadname + " : OperationID: " + operationID +  " Deq() : " + v);
        }
        else {
//          Utils.logInfo(threadname + " : OperationID: " + operationID +  " Deq() : " + value);
        }
        break;

      case "isEmpty":

//        Utils.logInfo(threadname + " : Invoking OperationID: " + operationID );
        result = queue.isEmpty();
//        Utils.logInfo(threadname + " : OperationID: " + operationID +  " isEmpty() : " + result);
        break;

      default:
        Utils.logInfo(threadname + ": Invalid operation: " + operationName);
    }
  }

}
