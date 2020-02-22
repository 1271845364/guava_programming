package com.yejinhui.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import com.sun.media.jfxmedia.locator.LocatorCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/16 15:13
 */
public class CacheLoaderTest {

    private boolean isTrue = false;

    @Test
    public void testBasic() throws ExecutionException, InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(10)
                .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                .build(createCacheLoader());

        Employee employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        assertLoadFromDBThenReset();
        employee = cache.get("Alex");
        assertThat(employee, notNullValue());
        assertLoadFromCache();

        TimeUnit.MILLISECONDS.sleep(31);
        employee = cache.get("Alex");
        assertLoadFromDBThenReset();
    }

    @Test
    public void testEvictionBySize() {
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        LoadingCache<String, Employee> loadingCache = CacheBuilder.newBuilder().maximumSize(3).build(cacheLoader);
        loadingCache.getUnchecked("Alex");
        assertLoadFromDBThenReset();

        loadingCache.getUnchecked("Jack");
        assertLoadFromDBThenReset();

        loadingCache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        assertThat(loadingCache.size(), equalTo(3L));

        loadingCache.getUnchecked("Susan");
        assertThat(loadingCache.size(), equalTo(3L));

        //Alex已经被逐出了，因为cache大小超过了3
        assertThat(loadingCache.getIfPresent("Alex"), nullValue());
        assertThat(loadingCache.getIfPresent("Susan"), notNullValue());
        //存在的Jack，因为Alex并没有在缓存中
        System.out.println(loadingCache.getUnchecked("Jack"));
    }

    @Test
    public void testEvictionByWeight() {
        CacheLoader<String, Employee> cacheLoader = createCacheLoader();
        Weigher<String, Employee> weigher = (key, employee) -> employee.getName().length() +
                employee.getDept().length() + employee.getEmpID().length();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .maximumWeight(45).concurrencyLevel(1).weigher(weigher).build(cacheLoader);

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Kevin");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Allen");
        assertLoadFromDBThenReset();

        assertThat(cache.size(), equalTo(3L));
        assertThat(cache.getIfPresent("Gavin"), notNullValue());

        cache.getUnchecked("Jason");
        assertThat(cache.getIfPresent("Kevin"), nullValue());
        assertThat(cache.size(),equalTo(3L));
    }

    private CacheLoader<String, Employee> createCacheLoader() {
        return new CacheLoader<String, Employee>() {
            @Override
            public Employee load(String key) throws Exception {
                return findEmployeeByName(key);
            }
        };
    }

    private void assertLoadFromDBThenReset() {
        assertThat(true, equalTo(isTrue));
        isTrue = false;
    }

    private void assertLoadFromCache() {
        assertThat(false, equalTo(isTrue));
    }

    private Employee findEmployeeByName(final String name) {
        System.out.println("The employee " + name + " is load from DB.");
        isTrue = true;
        return new Employee(name, name, name);
    }

}
