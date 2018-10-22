package com.avramenko.datastructures.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T>, Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == size) {
            newNode.prev = tail;
            if (tail != null)
                tail.next = newNode;
        } else {
            Node<T> cur = getNode(index);
            newNode.next = cur;
            newNode.prev = cur.prev;
            if (cur.prev != null)
                cur.prev.next = newNode;
            cur.prev = newNode;
        }
        if (newNode.prev == null) {
            head = newNode;
        }
        if (newNode.next == null) {
            tail = newNode;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> cur = getNode(index);
        if (cur.prev != null) {
            cur.prev.next = cur.next;
        } else {
            head = cur.next;
        }
        if (cur.next != null) {
            cur.next.prev = cur.prev;
        } else {
            tail = cur.prev;
        }
        size--;
        return cur.value;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if (index <= size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            Node<T> current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> curNode = getNode(index);
        T obj = curNode.value;
        curNode.value = value;
        return obj;
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        Node<T> curNode = head;
        int i = 0;
        while (curNode != null) {
            if (Objects.equals(curNode.value, value)) {
                return i;
            }
            curNode = curNode.next;
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        Node<T> curNode = tail;
        int i = size - 1;
        while (curNode != null) {
            if (Objects.equals(curNode.value, value))
                return i;
            curNode = curNode.prev;
            i--;
        }
        return -1;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object value : this) {
            joiner.add(value.toString());
        }
        return joiner.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private static class Node<T> {

        T value;
        Node<T> prev;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private class ListIterator implements Iterator {

        Node<T> cursor = head;

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public T next() {
            T value = cursor.value;
            cursor = cursor.next;
            return value;
        }
    }

    public static class Test {

        public static void main(String[] args) {

            List<String> list = new LinkedList<>();
            list.add("A");
            list.add("B");
            list.add("c");
            System.out.println(list);

            list.add("E");
            System.out.println("add(\"E\")");
            System.out.println(list);

            list.add("D", 3);
            System.out.println("add(\"D\", 3)");
            System.out.println(list);

            System.out.println("remove(4)");
            System.out.println(list.remove(4));
            System.out.println(list);

            System.out.println("set(\"C\", 2)");
            System.out.println(list.set("C", 2));
            System.out.println(list);

            System.out.println("indexOf(\"B\")");
            System.out.println(list.indexOf("B"));
            System.out.println(list);

            System.out.println("lastIndexOf(\"Z\")");
            System.out.println(list.lastIndexOf("Z"));
            System.out.println(list);

        }
    }
}
