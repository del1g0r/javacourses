package com.avramenko.datastructures.map;

import com.avramenko.datastructures.list.ArrayList;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap<K, V> implements Map<K, V>, Iterable<HashMap.Entry<K, V>> {

    static final private double CLUSTER_FACTOR = 0.5;
    static final private int INCREASE_FACTOR = 2;
    static final private int DEFAULT_BUCKET_COUNT = 10;

    private ArrayList<Entry<K, V>>[] buckets;

    @SuppressWarnings("unchecked")
    private HashMap(int bucketCount) {
        buckets = new ArrayList[bucketCount];
    }

    private HashMap() {
        this(DEFAULT_BUCKET_COUNT);
    }

    public void putAll(HashMap<K, V> map) {
        for (Entry<K, V> entry : map) {
            put(entry, true);
        }
    }

    @Override
    public V put(K key, V value) {
        return put(new Entry<>(key, value), true);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return put(new Entry<>(key, value), false);
    }

    @Override
    public V get(K key) {
        for (Entry<K, V> entry : getBucket(key)) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        Iterator<Entry<K, V>> iterator = getBucket(key).iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (Objects.equals(entry.key, key)) {
                V entryValue = entry.value;
                iterator.remove();
                return entryValue;
            }
        }
        return null;
    }

    @Override
    public int size() {
        int size = 0;
        for (ArrayList<Entry<K, V>> bucket : buckets) {
            size += bucket == null ? 0 : bucket.size();
        }
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        for (Entry<K, V> entry : getBucket(key)) {
            if (Objects.equals(entry.key, key)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Entry entry : this) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<Entry<K, V>> iterator() {
        return new MapIterator();
    }

    public static class Entry<K, V> {

        K key;
        V value;

        Entry(K key, V value) {
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

    private ArrayList<Entry<K, V>> getBucket(ArrayList<Entry<K, V>>[] buckets, K key) {
        int bucketIndex = (key == null ? 0 : Math.abs(key.hashCode())) % buckets.length;
        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<>();
        }
        return buckets[bucketIndex];
    }

    private ArrayList<Entry<K, V>> getBucket(K key) {
        return getBucket(buckets, key);
    }

    private V put(ArrayList<Entry<K, V>>[] buckets, Entry<K, V> entry, boolean growIfNeeded, boolean replaceIfExists) {
        ArrayList<Entry<K, V>> bucket = getBucket(buckets, entry.key);
        for (Entry<K, V> oldEntry : bucket) {
            if (Objects.equals(oldEntry.key, entry.key)) {
                V entryValue = oldEntry.value;
                if (replaceIfExists) {
                    oldEntry.value = entry.value;
                }
                return entryValue;
            }
        }
        if (growIfNeeded) {
            this.buckets = checkAndGrow(buckets);
            bucket = getBucket(buckets, entry.key);
        }
        bucket.add(entry);
        return null;
    }

    private V put(Entry<K, V> entry, boolean replaceIfExists) {
        return put(buckets, entry, true, replaceIfExists);
    }

    private ArrayList<Entry<K, V>>[] checkAndGrow(ArrayList<Entry<K, V>>[] buckets) {
        if (size() > buckets.length * CLUSTER_FACTOR) {
            @SuppressWarnings("unchecked")
            ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[buckets.length * INCREASE_FACTOR];
            //System.out.println("growing to " + newBuckets.length);
            for (Entry<K, V> entry : this) {
                put(newBuckets, entry, false, true);
            }
            return newBuckets;
        } else {
            return buckets;
        }
    }

    private class MapIterator implements Iterator {

        int indexBucket = -1;
        Iterator<Entry<K, V>> childIterator;

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
        public Entry<K, V> next() {
            return childIterator.next();
        }

        @Override
        public void remove() {
            childIterator.remove();
        }
    }

    public static class Test {

        public static void main(String[] args) {

            Map<String, String> map = new HashMap<>();

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
