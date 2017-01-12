package com.lyonsys.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class OptMap implements Map {


    private final int length;
    private final Object[] data;

    private final int entryPerBucket = 10;

    public OptMap(int length) {
        this.length = length;
        this.data = new Object[length];
    }

    @Override
    public Object put(Object key, Object value) {
        return null;
    }



    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
