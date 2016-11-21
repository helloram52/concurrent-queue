package com.multicore;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ram on 11/13/16.
 */
class Node<T> {

    private T value;
    private Node next;

    Node(T value) {
        this.value = value;
        this.next = null;
    }

    T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }
}

class LockFreeNode<T> extends Node {

    private AtomicReference<LockFreeNode> next;

    LockFreeNode(T value) {
        super(value);
        next = new AtomicReference<>(null);
    }

    @Override
    LockFreeNode getNext() {
        return this.next.get();
    }

    void setNext(LockFreeNode next) {
        this.next = new AtomicReference<>(next);
    }

    AtomicReference<LockFreeNode> getNextAtomicReference() {
        return this.next;
    }

    public void setNextAtomicReference(LockFreeNode node) {
        this.next = new AtomicReference<>(node);
    }
}

