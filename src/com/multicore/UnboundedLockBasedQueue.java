package com.multicore;

import java.util.EmptyStackException;

/**
 * Created by ram on 11/13/16.
 */
public class UnboundedLockBasedQueue<T> implements UnBoundedQueue<T> {

    private Node head;
    private Node tail;
    private Lock enqLock;
    private Lock deqLock;

    @SuppressWarnings("unchecked")
    public UnboundedLockBasedQueue() {
        head = new Node(Integer.MIN_VALUE);
//        tail = new Node(Integer.MAX_VALUE);
//        head.setNext(tail);
        tail = head;
        enqLock = new TestTestAndSet();
        deqLock = new TestTestAndSet();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void enq(T x) {
        enqLock.lock();
        try {
            Node<T> newNode = new Node<>(x);
            tail.setNext(newNode);
            tail = newNode;
        }
        finally {
            enqLock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deq() throws EmptyStackException {
        T result;
        deqLock.lock();
        try {
            if ( head.getNext() == null ) {
                throw new EmptyStackException();
            }
            result = (T) head.getNext().getValue();
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
