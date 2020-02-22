package com.yejinhui.guava.cache;

import com.google.common.cache.*;
import org.hamcrest.core.Is;
import org.junit.Test;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 23:03
 */
public class CacheLoaderTest3 {

    @Test
    public void testLoadNullValue() {
        CacheLoader<String, Employee> cacheLoader = CacheLoader.from(k -> k.equals("null") ? null : new Employee(k, k, k));
        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().build(cacheLoader);
        Employee alex = loadingCache.getUnchecked("Alex");

        assertThat(alex, notNullValue());
        assertThat(alex.getName(), equalTo("Alex"));

        try {
            assertThat(loadingCache.getUnchecked("null"), nullValue());
            fail("should not process to here.");
        } catch (Exception e) {
//            (expected = CacheLoader.InvalidCacheLoadException.class)
            assertThat(e instanceof CacheLoader.InvalidCacheLoadException, equalTo(true));
        }
    }

    @Test
    public void testLoadNullValueUseOptional() {
        CacheLoader<String, Optional<Employee>> cacheLoader = new CacheLoader<String, Optional<Employee>>() {
            @Override
            public Optional<Employee> load(String key) throws Exception {
                if (key.equals("null")) {
                    //Absent
                    return Optional.fromNullable(null);
                } else {
                    return Optional.fromNullable(new Employee(key, key, key));
                }
            }
        };

        LoadingCache<String, Optional<Employee>> cache = CacheBuilder.newBuilder().build(cacheLoader);
        assertThat(cache.getUnchecked("Alex").get(), notNullValue());
        assertThat(cache.getUnchecked("null").orNull(), nullValue());

        //这样就防止get时候出现NPE，此时返回的对象肯定不是null，因为对象如果是null，就走or()中的
        Employee def = cache.getUnchecked("null").or(new Employee("default", "default", "default"));
        assertThat(def.getName().length(), equalTo(7));
    }

    @Test
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String, Long> cacheLoader = CacheLoader.from(k -> {
            counter.incrementAndGet();
            return System.currentTimeMillis();
        });

        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                //假的refresh cache，里面是根据过期时间和当前时间进行比较的，来断定是否过期
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);
        Long result1 = cache.getUnchecked("Alex");
        TimeUnit.SECONDS.sleep(3);

        Long result2 = cache.getUnchecked("Alex");
//        assertThat(result1.longValue() != result2.longValue(), equalTo(true));

        assertThat(counter.get(), equalTo(2));
    }

    /**
     * 缓存提前加载
     */
    @Test
    public void testCachePreLoad() {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(cacheLoader);

        Map<String, String> preMap = new HashMap<String, String>() {
            {
                put("alex", "ALEX");
                put("hello", "hello");//这个数据是不符合cacheLoader规则的，但是也可以加入到cache，矛盾
            }
        };

        cache.putAll(preMap);
        assertThat(cache.size(), equalTo(2L));
        assertThat(cache.getUnchecked("alex"), equalTo("ALEX"));
        assertThat(cache.getUnchecked("hello"), equalTo("hello"));
    }

    @Test
    public void testCacheRemoveNotification() {
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener = notification -> {
            if (notification.wasEvicted()) {
                RemovalCause cause = notification.getCause();
                assertThat(cause, is(RemovalCause.SIZE));
                assertThat(notification.getKey(), equalTo("Alex"));
            }
        };

        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener)
                .build(cacheLoader);

        loadingCache.getUnchecked("Alex");
        loadingCache.getUnchecked("Eachur");
        loadingCache.getUnchecked("Jack");
        loadingCache.getUnchecked("Jenny");

    }
}