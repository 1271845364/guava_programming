package com.yejinhui.guava.cache;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 17:45
 */
public interface LRUCache<K, V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    void clear();

    /**
     * 缓存中可以存放多少元素
     *
     * @return
     */
    int limit();
}
