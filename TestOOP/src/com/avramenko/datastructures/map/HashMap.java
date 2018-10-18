package com.avramenko.datastructures.map;

import com.avramenko.datastructures.list.ArrayList;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap implements Map, Iterable {

    static private int defaultBucketCount = 3;

    ArrayList[] buckets;

    HashMap(int bucketCount) {
        buckets = new ArrayList[bucketCount];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList();
        }
    }

    HashMap() {
        this(defaultBucketCount);
    }

    private ArrayList getBucket(Object key) {
        return buckets[(key == null ? 0 : key.hashCode()) % buckets.length];
    }

    @Override
    public Object put(Object key, Object value) {

        ArrayList bucket = getBucket(key);

        for (Object entryObject : bucket) {
            Entry entry = (Entry) entryObject;
            if (Objects.equals(entry.key, key)) {
                Object entryValue = entry.value;
                entry.value = value;
                return entryValue;
            }
        }

        bucket.add(new Entry(key, value));
        return null;
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {

        ArrayList bucket = getBucket(key);

        for (Object entryObject : bucket) {
            Entry entry = (Entry) entryObject;
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }

        bucket.add(new Entry(key, value));
        return null;
    }

    @Override
    public Object get(Object key) {

        ArrayList bucket = getBucket(key);

        for (Object entryObject : bucket) {
            Entry entry = (Entry) entryObject;
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public Object remove(Object key) {

        ArrayList bucket = getBucket(key);

        int i = 0;
        for (Object entryObject : bucket) {
            Entry entry = (Entry) entryObject;
            if (Objects.equals(entry.key, key)) {
                Object entryValue = entry.value;
                bucket.remove(i);
                return entryValue;
            }
            i++;
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (int i = 0; i < buckets.length; i++) {
            size += buckets[i].size();
        }
        return size;
    }

    @Override
    public boolean containsKey(Object key) {

        ArrayList bucket = getBucket(key);

        for (Object entryObject : bucket) {
            Entry entry = (Entry) entryObject;
            if (Objects.equals(entry.key, key)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object value : this) {
            joiner.add(((Entry) value).toString());
        }
        return joiner.toString();
    }

    @Override
    public Iterator iterator() {
        return new MapIterator();
    }

    private static class Entry {

        Object key;
        Object value;

        public Entry(Object key, Object value) {

            this.key = key;
            this.value = value;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append('(');
            builder.append(key);
            builder.append(": ");
            builder.append(value);
            builder.append(')');
            return builder.toString();
        }
    }

    private class MapIterator implements Iterator {

        int cursorBucket = getNextBucket(-1);
        int cursor = 0;

        private int getNextBucket(int index) {
            for (int i = index + 1; i < buckets.length; i++) {
                if (!buckets[i].isEmpty()) {
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean hasNext() {
            return cursorBucket != -1;
        }

        @Override
        public Object next() {
            Object object = buckets[cursorBucket].get(cursor);
            cursor++;
            if (cursor == buckets[cursorBucket].size()) {
                cursorBucket = getNextBucket(cursorBucket);
                cursor = 0;
            }
            return object;
        }
    }

    public static class Test {

        public static void main(String[] args) {

            Map map = new HashMap();

            Object value = map.put("user", "john"); // put work as update

            System.out.println(value); // null

            value = map.put("user", "Ann");

            System.out.println(value); // "john"

            System.out.println(map.size()); // 1

            value = map.putIfAbsent("user", "Kate");

            System.out.println(value); // Ann

            value = map.putIfAbsent("password", "test");

            System.out.println(value); // null

            System.out.println(map.get("user")); // Ann

            System.out.println(map.remove("password")); // "test"

            System.out.println(map); // "test"
        }
    }
}
