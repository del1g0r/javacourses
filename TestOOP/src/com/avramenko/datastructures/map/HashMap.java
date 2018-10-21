package com.avramenko.datastructures.map;

import com.avramenko.datastructures.list.ArrayList;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap implements Map, Iterable {

    static private double CLUSTER_FACTOR = 0.5;
    static private int INCREASE_FACTOR = 2;
    static private int defaultBucketCount = 3;

    ArrayList[] buckets;

    HashMap(int bucketCount) {
        buckets = new ArrayList[bucketCount];
    }

    HashMap() {
        this(defaultBucketCount);
    }

    public void putAll(HashMap map) {
        for (Object object : map) {
            put(buckets, (Entry) object);
        }
    }

    @Override
    public Object put(Object key, Object value) {
        checkAndGrow();
        return put(buckets, key, value);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        checkAndGrow();

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
            size += buckets[i] == null ? 0 : buckets[i].size();
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

    private ArrayList getBucket(ArrayList[] buckets, Object key) {
        int bucketIndex = (key == null ? 0 : Math.abs(key.hashCode())) % buckets.length;
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList();
        }
        return buckets[bucketIndex];
    }

    private ArrayList getBucket(Object key) {
        return getBucket(buckets, key);
    }

    private void put(ArrayList[] buckets, Entry entry) {
        ArrayList bucket = getBucket(buckets, entry.key);
        bucket.add(entry);
    }

    private void checkAndGrow() {
        if (size() > buckets.length * CLUSTER_FACTOR) {
            ArrayList[] newBuckets = new ArrayList[buckets.length * INCREASE_FACTOR];
            //System.out.println("growing to " + newBuckets.length);
            for (Object entryObj : this) {
                put(newBuckets, (Entry) entryObj);
            }
            buckets = newBuckets;
        }
    }

    private Object put(ArrayList[] buckets, Object key, Object value) {

        ArrayList bucket = getBucket(buckets, key);

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

    private class MapIterator implements Iterator {

        int indexBucket = -1;
        Iterator childIterator;

        private int getNextBucket(int index) {
            for (int i = index + 1; i < buckets.length; i++) {
                if (buckets[i] != null && !buckets[i].isEmpty()) {
                    childIterator = buckets[i].iterator();
                    return i;
                }
            }
            childIterator = null;
            return -1;
        }

        @Override
        public boolean hasNext() {
            return (childIterator != null && childIterator.hasNext())
                    || ((indexBucket = getNextBucket(indexBucket)) != -1);
        }

        @Override
        public Object next() {
            return childIterator.next();
        }

        @Override
        public void remove() {
            childIterator.remove();
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

            for (int i = 0; i < 100; i++) {
                map.put("key" + i, "value" + i);
            }

            System.out.println(map); // "test"
        }
    }
}
