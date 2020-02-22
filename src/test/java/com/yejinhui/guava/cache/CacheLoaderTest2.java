package com.yejinhui.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 20:23
 */
public class CacheLoaderTest2 {

    /**
     * ttl -> time to live = 存活时间
     * Access time => Write/Update/Read
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByAccessTime() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());
        assertThat(cache.get("Alex"), notNullValue());
        TimeUnit.SECONDS.sleep(2);

        //下面证明了guava的缓存实现了不同的key的存活时间都是固定时长，从设置key的时候开始计算时间（access）
        cache.get("Kevin");
        assertThat(cache.getIfPresent("Alex"), nullValue());
        assertThat(cache.getIfPresent("Kevin"), notNullValue());

        assertThat(cache.getUnchecked("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.SECONDS.sleep(1);

        assertThat(cache.getIfPresent("Guava"), notNullValue());

    }

    /**
     * Write time => write/update
     */
    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(this.createCacheLoader());

        assertThat(cache.getUnchecked("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), notNullValue());
        TimeUnit.MILLISECONDS.sleep(990);
        assertThat(cache.getIfPresent("Guava"), notNullValue());

        TimeUnit.SECONDS.sleep(1);
        assertThat(cache.getIfPresent("Guava"), nullValue());
    }

    /**
     * Strong/Soft/Weak/Phantom reference
     */
    @Test
    public void testWeakKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .weakValues()
                .weakKeys()
                .build(this.createCacheLoader());
        assertThat(cache.getUnchecked("Alex"), notNullValue());
        assertThat(cache.getUnchecked("Guava"), notNullValue());

        //active method = 同步调用的异步方法
        //Thread active design pattern
        System.gc();

        TimeUnit.MILLISECONDS.sleep(100);//100毫秒足够gc回收weak引用了
        assertThat(cache.getIfPresent("Alex"), nullValue());
    }

    @Test
    public void testSoftKey() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.SECONDS)
                .softValues()
                .build(this.createCacheLoader());
        int i = 0;
        for (; ; ) {
            cache.put("Alex" + i, new Employee("Alex" + 1, "Alex" + 1, "Alex" + 1));
            i++;
            System.out.println("The Employee [" + i + "] is store into cache.");
            //休眠下，不能一直发，因为gc的线程是另外一个线程，不然来不及回收
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    /**
     * private修饰的方法不需要final，因为private修饰的方法本身就不能被重写
     *
     * @return
     */
    private CacheLoader<String, Employee> createCacheLoader() {
        return CacheLoader.<String, Employee>from(k -> new Employee(k, k, k));
    }

}
