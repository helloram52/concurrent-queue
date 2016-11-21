/*
 * Copyright (c) 2014 Cloudvisory LLC. All rights reserved.
 */
package com.multicore;

/**
 * Created by vadivel on 11/17/16.
 */
public class TestQueueItem {
  public TestQueueItem(int threadID, int value) {
    this.threadID = threadID;
    this.value = value;
  }

  public int getThreadID() {
    return threadID;
  }

  public void setThreadID(int threadID) {
    this.threadID = threadID;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  int threadID;
  int value;

  @Override public String toString() {
    return "TestQueueItem{" +
      "threadID=" + threadID +
      ", value=" + value +
      '}';
  }
}
