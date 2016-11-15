package com.multicore;

import sun.invoke.empty.Empty;

import java.util.EmptyStackException;

/**
 * Created by ram on 11/13/16.
 */
public class UnboundedLockBasedQueue implements UnBoundedQueue {

    private Node head;
    private Node tail;
    private Lock enqLock;
    private Lock deqLock;

    public UnboundedLockBasedQueue() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        enqLock = new TestTestAndSet();
        deqLock = new TestTestAndSet();
    }

    @Override
    public void enq(int x) {
        enqLock.lock();
        try {
            Node newNode = new Node(x);
            tail.setNext(newNode);
            tail = newNode;
        }
        finally {
            enqLock.unlock();
        }
    }

    @Override
    public int deq() throws EmptyStackException {
        int result;
        deqLock.lock();
        try {
            if( head.getNext() == null ) {
                Utils.logInfo("throwing empty stack exception");
                throw new EmptyStackException();
            }
            result = head.getNext().getValue();
            head = head.getNext();
            return result;
        }
        finally {
            deqLock.unlock();
        }


    }

    public boolean isEmpty() {
        return head.getNext() == null;
    }
}
