package com.avramenko.datastructures.map;


public interface Map<K,V> {

    V put(K key, V value);

    V putIfAbsent(K key, V value);

    V get(K key);

    V remove(K key);

    int size();

    boolean containsKey(K key);
}

