package com.multicore;

/**
 * Enum class describing the various modes of running
 * the experiment.
 */
public enum RunMode {
  ENQ_DEQ(50, 50, 0, "Only Enq and Deq"),
  WRITE_DOMINATED(40, 40, 20, "Enq, Deq with isEmpty");

  int percentageOfInserts, percentageOfDeletes, percentageOfSearches;
  String modeName;
  RunMode(int percentageOfInserts, int percentageOfDeletes, int percentageOfSearches, String modeName) {
    this.percentageOfInserts = percentageOfInserts;
    this.percentageOfDeletes = percentageOfDeletes;
    this.percentageOfSearches = percentageOfSearches;
    this.modeName = modeName;
  }
}
