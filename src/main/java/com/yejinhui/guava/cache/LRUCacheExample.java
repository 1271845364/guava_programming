package com.yejinhui.guava.cache;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 11:11
 */
public class LRUCacheExample {

    public static void main(String[] args) throws InterruptedException {
        final LinkedHashLRUCache<String, byte[]> cache = new LinkedHashLRUCache<>(100);
        for (int i = 0; i < 100; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println("The " + i + " entity is cached.");
        }

    }
}
