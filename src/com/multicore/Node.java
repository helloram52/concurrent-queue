package com.multicore;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Ram on 11/13/16.
 */
class Node {

    private int value;
    private Node next;

    Node(int value) {
        this.value = value;
        this.next = null;
    }

    int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    Node getNext() {
        return next;
    }

    void setNext(Node next) {
        this.next = next;
    }
}

class LockFreeNode extends Node {

    private AtomicReference<LockFreeNode> next;

    LockFreeNode(int value) {
        super(value);
        next = new AtomicReference<>(null);
    }

    @Override
    LockFreeNode getNext() {
        return this.next.get();
    }

    AtomicReference<LockFreeNode> getNextAtomicReference() {
        return this.next;
    }

    public void setNextAtomicReference(LockFreeNode node) {
        this.next = new AtomicReference<>(node);
    }
}

