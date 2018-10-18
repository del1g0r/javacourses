package com.avramenko.datastructures.queue;

public interface Queue {

    void enqueue(Object value);

    Object dequeue();

    Object peek();

    int size();
}