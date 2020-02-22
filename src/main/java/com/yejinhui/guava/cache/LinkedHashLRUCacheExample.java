package com.yejinhui.guava.cache;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 18:34
 */
public class LinkedHashLRUCacheExample {

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LinkedHashLRUCache<>(3);
        for (int i = 1; i <= 3; i++) {
            cache.put(i + "", i + "");
        }

        System.out.println(cache);
        cache.put("4","4");
        System.out.println(cache);

        cache.get("2");
        System.out.println(cache);

    }
}
