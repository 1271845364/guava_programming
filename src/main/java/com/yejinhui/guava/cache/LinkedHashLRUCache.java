package com.yejinhui.guava.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class is not the thread-safe class.
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 17:47
 */
public class LinkedHashLRUCache<K, V> implements LRUCache<K, V> {

    /**
     * private的，并且继承LinkedHashMap目的：为了屏蔽LinkedHashMap中的其他方法，外部类中只暴露了接口中的方法，更好的体现封装
     * ，而采用组合的方式
     *
     * @param <K>
     * @param <V>
     */
    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int limit;

        public InternalLRUCache(int limit) {
            //存储和访问保证顺序一致
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > this.limit;
        }
    }

    private final InternalLRUCache<K, V> internalLRUCache;

    private final int limit;

    public LinkedHashLRUCache(int limit) {
        checkArgument(limit > 0, "The limit big than zero.");
        this.limit = checkNotNull(limit);
        this.internalLRUCache = new InternalLRUCache(limit);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public String toString() {
        return this.internalLRUCache.toString();
    }
}
