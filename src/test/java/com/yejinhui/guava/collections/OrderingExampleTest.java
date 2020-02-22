package com.yejinhui.guava.collections;

import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/20 22:54
 */
public class OrderingExampleTest {

    @Test
    public void testJDKOrder() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        System.out.println(list);
        //自然顺序排序，如果list中存在null，会出现空指针异常
        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalByNullFirst() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list, Ordering.natural().nullsFirst());
        System.out.println(list);
    }

    @Test
    public void testOrderNaturalByNullLast() {
        List<Integer> list = Arrays.asList(1, 5, null, 3, 8, 2);
        Collections.sort(list, Ordering.natural().nullsLast());
        System.out.println(list);
    }

    @Test
    public void testOrderNatural() {
        List<Integer> list = Arrays.asList(1, 5, 3, 8, 2);
        Collections.sort(list, Ordering.natural().reverse());
        System.out.println(list);
    }
}
