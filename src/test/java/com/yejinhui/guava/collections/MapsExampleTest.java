package com.yejinhui.guava.collections;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/19 22:06
 */
public class MapsExampleTest {

    @Test
    public void testCreate() {
        ArrayList<String> valueList = Lists.newArrayList("1", "2", "3");
        ImmutableMap<String, String> immutableMap = Maps.uniqueIndex(valueList, k -> k + "_key");
        System.out.println(immutableMap);

        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        System.out.println(map);
    }

    @Test
    public void testTransform() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        Map<String, String> map1 = Maps.transformValues(map, v -> v + "_transform");
        System.out.println(map1);
    }

    @Test
    public void testFilter() {
        Map<String, String> map = Maps.asMap(Sets.newHashSet("1", "2", "3"), k -> k + "_value");
        //只要key是1,2
        Map<String, String> map1 = Maps.filterKeys(map, k -> Lists.newArrayList("1", "2").contains(k));
        System.out.println(map1);
        assertThat(map1.containsKey("3"), is(false));

    }

}
