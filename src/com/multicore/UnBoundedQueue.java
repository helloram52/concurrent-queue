package com.multicore;

/**
 * Created by Ram on 11/13/16.
 */
public interface UnBoundedQueue<T> {

    void enq(T x);
    T deq();
    boolean isEmpty();
}
