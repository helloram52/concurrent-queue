package com.multicore;

import sun.util.resources.cldr.lag.LocaleNames_lag;

import java.util.EmptyStackException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ram on 11/13/16.
 */
public class UnBoundedLockFreeQueue<T> implements UnBoundedQueue<T> {

    private AtomicReference<LockFreeNode> head;
    private AtomicReference<LockFreeNode> tail;

    @SuppressWarnings("unchecked")
    UnBoundedLockFreeQueue() {
        LockFreeNode<Integer> sentinel = new LockFreeNode(Integer.MIN_VALUE);
        head = new AtomicReference<>(sentinel);
        tail = new AtomicReference<>(sentinel);
//        head.get().setNext(tail.get());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void enq(T x) {
        LockFreeNode newNode = new LockFreeNode(x);
        while(true) {
            LockFreeNode last = tail.get();
            LockFreeNode next = last.getNext();
            if( last == tail.get() ) {
                if( next == null ) {
                    if( last.getNextAtomicReference().compareAndSet(next, newNode) ) {
                        tail.compareAndSet(last, newNode);
                        return;
                    }
                }
                else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deq() throws EmptyStackException {
        while( true ) {
            LockFreeNode first = head.get();
            LockFreeNode last = tail.get();
            LockFreeNode next = first.getNext();

            if( first == head.get() ) {
                if( first == last ) {
                    if( next == null ) {
                        throw new EmptyStackException();
                    }
                    tail.compareAndSet(last, next);
                }
                else {
                    T value = (T) next.getValue();
                    if( head.compareAndSet(first, next) ) {
                        return value;
                    }
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return head.get().getNext() == null;
    }
}
