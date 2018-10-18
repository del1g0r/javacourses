package com.avramenko.datastructures.map;


public interface Map {

    Object put(Object key, Object value);

    Object putIfAbsent(Object key, Object value);

    Object get(Object key);

    Object remove(Object key);

    int size();

    boolean containsKey(Object key);
}

