package com.avramenko.datastructures.queue;

import java.util.Iterator;
import java.util.StringJoiner;

public class ArrayQueue implements Queue, Iterable {

    Object[] array;
    int size;

    ArrayQueue(int initSize) {
        super();
        array = new Object[initSize];
    }

    ArrayQueue() {
        this(5);
    }

    ArrayQueue(Object[] array) {
        this.array = array;
        size = array.length;
    }

    public void enqueue(Object value) {
        Object[] newArray = size == array.length ? new Object[array.length * 3 / 2 + 1] : array;
        for (int i = size - 1; i >= 0; i--)
            newArray[i + 1] = array[i];
        newArray[0] = value;
        array = newArray;
        size++;
    }

    public Object dequeue() {
        size--;
        return array[size];
    }

    public Object peek() {
        return array[size - 1];
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object value : this) {
            joiner.add(value.toString());
        }
        return joiner.toString();
    }

    @Override
    public Iterator iterator() {
        return new QueueIterator();
    }

    private static class Node {

        Object value;
        Node next;

        public Node(Object value) {
            this.value = value;
        }

    }

    private class QueueIterator implements Iterator {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public Object next() {
            Object object = array[cursor];
            cursor++;
            return object;
        }
    }

    public static class Test {

        public static void main(String[] args) {

            System.out.println("Test queue:");

            Queue queue = new ArrayQueue(new Object[]{"A", "B", "C"});

            System.out.println(queue);

            String obj = "D";
            System.out.println(obj + " is being enqueued");
            queue.enqueue(obj);

            System.out.println(queue.peek() + " is ready to be dequeued");
            System.out.println(queue);
            System.out.println(queue.dequeue() + " is dequeued");
            System.out.println(queue);
        }
    }
}
