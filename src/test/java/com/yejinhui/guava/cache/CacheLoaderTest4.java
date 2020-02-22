package com.yejinhui.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/17 21:35
 */
public class CacheLoaderTest4 {

    @Test
    public void testCacheStat() {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                //记录统计
                .recordStats()
                .build(cacheLoader);
        assertCache(cache);

        List<String> list = null;
        list.iterator();
    }

    @Test
    public void testCacheSpec() {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        String spec = "maximumSize=5,recordStats";
        CacheBuilderSpec cacheBuilderSpec = CacheBuilderSpec.parse(spec);
        LoadingCache<String, String> cache = CacheBuilder.from(cacheBuilderSpec).build(cacheLoader);

        assertCache(cache);

    }

    private void assertCache(LoadingCache<String, String> cache) {
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));
        CacheStats cacheStats = cache.stats();
        System.out.println(cacheStats.hashCode());
        //命中个数
        assertThat(cacheStats.hitCount(), equalTo(0L));
        //缺失个数
        assertThat(cacheStats.missCount(), equalTo(1L));

        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));

        //这么设计CacheStats的目的，多个线程去访问cache，并且同时产生CacheStats的时候，他们是不会去影响的
        cacheStats = cache.stats();
        System.out.println(cacheStats.hashCode());
        assertThat(cacheStats.hitCount(), equalTo(1L));
        assertThat(cacheStats.missCount(), equalTo(1L));

        System.out.println(cacheStats.hitRate());
        System.out.println(cacheStats.missRate());
    }
}
