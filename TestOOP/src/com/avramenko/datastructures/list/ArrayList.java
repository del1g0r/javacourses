package com.avramenko.datastructures.list;

import com.avramenko.datastructures.list.List;

public class ArrayList implements List {

    Object[] array;
    int size;

    ArrayList(int initSize) {
        super();
        array = new Object[initSize];
    }

    ArrayList() {
        this(5);
    }

    ArrayList(Object[] array) {
        this.array = array;
        size = array.length;
    }

    public void add(Object value) {
        if (size == array.length) {
            Object[] newArray = new Object[array.length * 3 / 2];
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
            Object[] newArray = size == array.length ? new Object[array.length * 3 / 2] : array;
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
            if (array[i].equals(value))
                return i;
        return -1;
    }

    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--)
            if (array[i].equals(value))
                return i;
        return -1;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < size; i++)
            res += (i == 0 ? "" : ", ") + array[i].toString();
        return "[" + res + "]";
    }
}
