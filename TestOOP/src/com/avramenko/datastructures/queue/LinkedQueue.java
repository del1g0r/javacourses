package com.avramenko.datastructures.queue;

import java.util.Iterator;
import java.util.StringJoiner;

public class LinkedQueue implements Queue, Iterable {

    int size;
    Node head;

    LinkedQueue(Object[] array) {
        for (int i = 0; i < array.length; i++)
            enqueue(array[i]);
    }

    @Override
    public void enqueue(Object value) {
        Node newNode = new Node(value);
        if (size == 0) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public Object dequeue() {
        Object value = head.value;
        head = head.next;
        size--;
        return value;
    }

    @Override
    public Object peek() {
        return head.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

        Node cursor = head;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Object next() {
            Object value = cursor.value;
            cursor = cursor.next;
            return value;
        }
    }

    public static class Test {

        public static void main(String[] args) {

            System.out.println("Test queue:");

            Queue queue = new LinkedQueue(new Object[]{"A", "B", "C"});

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
