package com.yejinhui.guava.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * value为SoftReference，可以防止本地缓存导致OOM的发生，当JVM要发生OOM的时候，GC会尝试回收JVM中的SoftReference引用的对象
 *
 * SoftReference的回收不是说急需的时候回收，jvm侦探到内存不够，快要OOM的时候去尝试回收在JVM当中的所有SoftReference
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 12:10
 */
public class SoftLRUCache<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, SoftReference<V>> {
        private final int limit;

        public InternalLRUCache(int limit) {
            //存储和访问保证顺序一致
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return this.size() > limit;
        }
    }

    private final int limit;

    private final InternalLRUCache<K, V> cache;

    public SoftLRUCache(int limit) {
        this.limit = limit;
        this.cache = new InternalLRUCache<>(limit);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, new SoftReference<>(value));
    }

    @Override
    public V get(K key) {
        SoftReference<V> softReference = this.cache.get(key);
        if (softReference == null) {
            return null;
        }
        //可能返回的是null，被gc回收了返回就是null
        return softReference.get();
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public int limit() {
        return this.limit;
    }
}
