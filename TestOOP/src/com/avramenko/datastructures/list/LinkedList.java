package com.avramenko.datastructures.list;

public class LinkedList implements List {

    Node head;
    Node tail;
    int size;

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        Node newNode = new Node(value);
        if (index == size) {
            newNode.prev = tail;
            if (tail != null)
                tail.next = newNode;
        } else {
            Node cur = getNode(index);
            newNode.next = cur;
            newNode.prev = cur.prev;
            if (cur.prev != null)
                cur.prev.next = newNode;
            cur.prev = newNode;
        }
        if (newNode.prev == null)
            head = newNode;
        if (newNode.next == null)
            tail = newNode;
        size++;
    }

    @Override
    public Object remove(int index) {
        Node cur = getNode(index);
        if (cur.prev != null)
            cur.prev.next = cur.next;
        else
            head = cur.next;
        if (cur.next != null)
            cur.next.prev = cur.prev;
        else
            tail = cur.prev;
        size--;
        return cur.value;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else if (index <= size / 2) {
            Node cur = head;
            for (int i = 0; i <= size / 2; i++)
                if (i == index) return cur;
                else cur = cur.next;
        } else {
            Node cur = tail;
            for (int i = size - 1; i > size / 2; i--)
                if (i == index) return cur;
                else cur = cur.prev;
        }
        return null;
    }

    @Override
    public Object get(int index) {
        return getNode(index).value;
    }

    @Override
    public Object set(Object value, int index) {
        Node curNode = getNode(index);
        Object obj = curNode.value;
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
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(Object value) {
        Node curNode = head;
        int i = 0;
        while (curNode != null) {
            if (curNode.value.equals(value))
                return i;
            curNode = curNode.next;
            i++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        Node curNode = tail;
        int i = size - 1;
        while (curNode != null) {
            if (curNode.equals(value))
                return i;
            curNode = curNode.prev;
            i--;
        }
        return -1;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Node curNode = head;
        while (curNode != null) {
            if (curNode.prev != null)
                builder.append(", ");
            builder.append(curNode.value.toString());
            curNode = curNode.next;
        }
        builder.append("]");
        return builder.toString();
    }

}