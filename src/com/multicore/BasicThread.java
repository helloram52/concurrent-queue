package com.multicore;

public class BasicThread implements Runnable {

  private int operationID;
  private String operationName;
  private int key;
  private UnBoundedQueue queue;

  public int getOperationID() {
    return operationID;
  }

  public BasicThread(int operationID, UnBoundedQueue queue, String operationName, int key) {
    this.operationID = operationID;
    this.queue = queue;
    this.operationName = operationName;
    this.key = key;
  }

  public void run() {

    int value;
    boolean result;
    String threadname = Thread.currentThread().getName();

    switch (operationName) {
      case "enq":

//        Utils.logInfo(threadname + " : Invoking OperationID: " + operationID );
        queue.enq(key);
//        Utils.logInfo(threadname + " : OperationID: " + operationID +  " Enq(" + key + ")");
        break;

      case "deq":

//        Utils.logInfo(threadname + " : Invoking OperationID: " + operationID );
        try {
          value = queue.deq();
        }
        catch (Exception e) {
//          Utils.logInfo("Exception e: " + e.getStackTrace());
          value = -1;
        }
//        Utils.logInfo(threadname + " : OperationID: " + operationID +  " Deq() : " + value);
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
