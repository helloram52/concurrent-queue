package com.multicore;

/**
 * Enum class describing the various modes of running
 * the experiment.
 */
public enum RunMode {
  ENQ_DEQ(80, 20, 0, "Enq_Deq"),
  MIXED(40, 40, 20, "Enq_Deq_isEmpty");

  int percentageOfEnqueue, percentageOfDequeue, percentageOfIsEmpty;
  String modeName;
  RunMode(int percentageOfEnqueue, int percentageOfDequeue, int percentageOfIsEmpty, String modeName) {
    this.percentageOfEnqueue = percentageOfEnqueue;
    this.percentageOfDequeue = percentageOfDequeue;
    this.percentageOfIsEmpty = percentageOfIsEmpty;
    this.modeName = modeName;
  }
}
