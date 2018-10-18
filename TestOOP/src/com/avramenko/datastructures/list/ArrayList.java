package com.avramenko.datastructures.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList implements List, Iterable {

    Object[] array;
    int size;

    public ArrayList(int initSize) {
        super();
        array = new Object[initSize];
    }

    public ArrayList() {
        this(5);
    }

    public ArrayList(Object[] array) {
        this.array = array;
        size = array.length;
    }

    public void add(Object value) {
        if (size == array.length) {
            Object[] newArray = new Object[array.length * 3 / 2 + 1];
            for (int i = 0; i < array.length; i++)
                newArray[i] = array[i];
            array = newArray;
        }
        array[size] = value;
        size++;
    }

    public void add(Object value, int index) {
        if (index == size) {
            // Fast insert
            add(value);
        } else if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        else {
            Object[] newArray = size == array.length ? new Object[array.length * 3 / 2 + 1] : array;
            for (int i = size - 1; i >= 0; i--)
                newArray[i + (index <= i ? 1 : 0)] = array[i];
            array = newArray;
            array[index] = value;
            size++;
        }
    }

    public Object remove(int index) {
        Object obj = get(index);
        size--;
        for (int i = 0; i < size; i++)
            array[i] = array[i + (index <= i ? 1 : 0)];
        return obj;
    }

    public Object get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    public Object set(Object value, int index) {
        Object obj = get(index);
        array[index] = value;
        return obj;
    }

    public void clear(boolean clearInstance) {
        if (clearInstance)
            array = new Object[2];
        size = 0;
    }

    public void clear() {
        clear(false);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    public int indexOf(Object value) {
        for (int i = 0; i < size; i++)
            if (Objects.equals(array[i], value))
                return i;
        return -1;
    }

    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--)
            if (Objects.equals(array[i], value))
                return i;
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
    public Iterator iterator() {
        return new ListIterator();
    }

    private static class Node {

        Object value;
        Node prev;
        Node next;

        public Node(Object value) {
            this.value = value;
        }
    }

    private class ListIterator implements Iterator {

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

            List list = new ArrayList();
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
