package com.avramenko.datastructures.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T>, Iterable<T> {

    private T[] array;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList(int initSize) {
        array = (T[]) new Object[initSize];
    }

    public ArrayList() {
        this(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Iterable)) {
            return false;
        }
        Iterator iterator = ((Iterable) obj).iterator();
        for (T value : this) {
            if (!iterator.hasNext()) {
                return false;
            }
            if (!Objects.equals(value, iterator.next())) {
                return false;
            }
        }
        return !iterator.hasNext();
    }

    public ArrayList(T[] array) {
        this.array = array;
        size = array.length;
    }

    public void add(T value) {
        if (size == array.length) {
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[array.length * 3 / 2 + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
        array[size] = value;
        size++;
    }

    public void add(T value, int index) {
        if (index == size) {
            // Fast insert
            add(value);
        } else if (index < 0 || index > size)
            throw new IndexOutOfBoundsException();
        else {
            @SuppressWarnings("unchecked")
            T[] newArray = size == array.length ? (T[]) new Object[array.length * 3 / 2 + 1] : array;
            for (int i = size - 1; i >= 0; i--)
                newArray[i + (index <= i ? 1 : 0)] = array[i];
            array = newArray;
            array[index] = value;
            size++;
        }
    }

    public T remove(int index) {
        T obj = get(index);
        size--;
        for (int i = 0; i < size; i++)
            array[i] = array[i + (index <= i ? 1 : 0)];
        return obj;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    public T set(T value, int index) {
        T obj = get(index);
        array[index] = value;
        return obj;
    }

    @SuppressWarnings("unchecked")
    public void clear(boolean clearInstance) {
        if (clearInstance)
            array = (T[]) new Object[0];
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

    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++)
            if (Objects.equals(array[i], value))
                return i;
        return -1;
    }

    public int lastIndexOf(T value) {
        for (int i = size - 1; i >= 0; i--)
            if (Objects.equals(array[i], value))
                return i;
        return -1;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (T value : this) {
            joiner.add(value.toString());
        }
        return joiner.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator {

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T object = array[index];
            index++;
            return object;
        }

        @Override
        public void remove() {
            size--;
            for (int i = 0; i < size; i++)
                array[i] = array[i + (index <= i ? 1 : 0)];
        }
    }

    public static class Test {

        public static void main(String[] args) {

            List<String> list = new ArrayList<>();
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
