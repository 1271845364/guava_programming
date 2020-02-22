package com.yejinhui.guava.cache;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class is not the thread-safe class.
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 18:42
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    //来保证顺序
    private final LinkedList<K> keys = new LinkedList<>();

    //存储
    private final Map<K, V> cache = new HashMap<>();

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
    }

    @Override
    public void put(K key, V value) {
        //容错处理
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        if (keys.size() >= this.limit) {
            //缓存满了，移除最近最少使用的
            K oldKey = keys.removeFirst();
            cache.remove(oldKey);
        }
        keys.addLast(key);
        cache.put(key, value);
    }

    @Override
    public V get(K key) {
        boolean exist = keys.remove(key);
        if (!exist) {
            return null;
        }
        keys.addLast(key);
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        boolean exist = keys.remove(key);
        if (exist) {
            cache.remove(key);
        }
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public void clear() {
        keys.clear();
        cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        return "LinkedListLRUCache{" +
                "keys=" + keys +
                ", cache=" + cache +
                '}';
    }
}
