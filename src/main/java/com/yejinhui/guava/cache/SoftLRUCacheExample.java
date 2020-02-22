package com.yejinhui.guava.cache;

import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 11:11
 */
public class SoftLRUCacheExample {

    public static void main(String[] args) throws InterruptedException {
        final SoftLRUCache<String, byte[]> cache = new SoftLRUCache<>(100);
        for (int i = 0; i < 1000; i++) {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached.");
        }

    }
}
