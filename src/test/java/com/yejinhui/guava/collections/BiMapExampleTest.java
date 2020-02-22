package com.yejinhui.guava.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/19 22:49
 */
public class BiMapExampleTest {

    /**
     * BiMap是以value为核心的
     */
    @Test
    public void testCreate() {
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "2");
        hashBiMap.put("1", "3");
        assertThat(hashBiMap.containsKey("1"), is(true));
        assertThat(hashBiMap.size(), equalTo(1));

        try {
            hashBiMap.put("2", "3");
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Map中的key和value互换，前提是HashBiMap中的key不能重复，value也不能重复
     */
    @Test
    public void testInverse() {
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "2");
        hashBiMap.put("2", "3");
        hashBiMap.put("3", "4");
        assertThat(hashBiMap.size(), equalTo(3));
        assertThat(hashBiMap.containsKey("1"), is(true));
        assertThat(hashBiMap.containsKey("2"), is(true));
        assertThat(hashBiMap.containsKey("3"), is(true));

        BiMap<String, String> inverse = hashBiMap.inverse();
        assertThat(inverse.size(), equalTo(3));
        assertThat(inverse.containsKey("2"), is(true));
        assertThat(inverse.containsKey("4"), is(true));
        assertThat(inverse.containsKey("3"), is(true));

        System.out.println(inverse);
    }

    @Test
    public void test() {
        HashBiMap<String, String> hashBiMap = HashBiMap.create();
        hashBiMap.put("1", "2");
        assertThat(hashBiMap.containsKey("1"), is(true));

        //执行了forcePut相当于前面的数据给清空了
        hashBiMap.forcePut("2", "2");
        assertThat(hashBiMap.size(), equalTo(1));
        assertThat(hashBiMap.containsKey("1"), is(false));
        assertThat(hashBiMap.containsKey("2"), is(true));
    }

}
