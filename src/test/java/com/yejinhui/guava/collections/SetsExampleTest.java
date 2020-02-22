package com.yejinhui.guava.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/19 21:37
 */
public class SetsExampleTest {

    @Test
    public void testCreate() {
        HashSet<Integer> hashSet = Sets.newHashSet(1, 2, 3);
//        Set<Integer> sets = new HashSet<Integer>() {
//            {
//                add(1);
//                add(2);
//                add(3);
//            }
//        };
        assertThat(hashSet.size(), equalTo(3));

        ArrayList<Integer> arrayList = Lists.newArrayList(1, 1, 2, 3);
        assertThat(arrayList.size(), equalTo(4));

        HashSet<Integer> hashSet1 = Sets.newHashSet(arrayList);
        assertThat(hashSet1.size(), equalTo(3));

    }

    /**
     * 笛卡尔积
     */
    @Test
    public void testCartesianProduct() {
        Set<List<Integer>> set = Sets.cartesianProduct(Sets.newHashSet(1, 2),
                Sets.newHashSet(3, 4),
                Sets.newHashSet(5, 6));
        System.out.println(set);
    }

    @Test
    public void testDifference() {
        HashSet<Integer> hashSet = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> hashSet1 = Sets.newHashSet(1, 4, 6);
        //差集
        Sets.SetView<Integer> difference = Sets.difference(hashSet, hashSet1);
        System.out.println(difference);

    }

    @Test
    public void testIntersection() {
        HashSet<Integer> hashSet = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> hashSet1 = Sets.newHashSet(1, 4, 6);
        //交集
        Sets.SetView<Integer> intersection = Sets.intersection(hashSet, hashSet1);
        System.out.println(intersection);
    }

    @Test
    public void test() {
        HashSet<Integer> hashSet = Sets.newHashSet(1, 2, 3);
        HashSet<Integer> hashSet1 = Sets.newHashSet(1, 4, 6);
        //并集
        Sets.SetView<Integer> union = Sets.union(hashSet, hashSet1);
        System.out.println(union);
    }
}
