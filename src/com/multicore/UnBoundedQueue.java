package com.multicore;

/**
 * Created by Ram on 11/13/16.
 */
public interface UnBoundedQueue {

    void enq(int x);
    int deq();
    boolean isEmpty();
}
