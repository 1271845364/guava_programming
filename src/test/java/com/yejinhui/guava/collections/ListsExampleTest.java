package com.yejinhui.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/19 20:27
 */
public class ListsExampleTest {

    @Test
    public void testCartesianProduct() {
        //笛卡尔积
        List<List<String>> list = Lists.cartesianProduct(Lists.newArrayList("1", "2"),
                Lists.newArrayList("3", "4"), Lists.newArrayList("5", "6"));
        System.out.println(list);
    }

    @Test
    public void testTransform() {
        ArrayList<String> list = Lists.newArrayList("Scala", "Guava", "Lists");
        Lists.transform(list, t -> t.toUpperCase()).forEach(System.out::println);

        ImmutableList<Character> abc = Lists.charactersOf("abc");
        System.out.println(abc);
    }

    @Test
    public void testNewArrayListWithCapacity() {
        ArrayList<String> arrayList = Lists.newArrayListWithCapacity(2);
        arrayList.add("x");
        arrayList.add("y");
        arrayList.add("z");
        System.out.println(arrayList);
    }

    @Test
    public void testNewArrayListWithExpectedSize() {
        Lists.newArrayListWithExpectedSize(5);
    }

    @Test
    public void testReverse() {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3");
        assertThat(Joiner.on(",").join(list), equalTo("1,2,3"));

        List<String> reverse = Lists.reverse(list);
        assertThat(Joiner.on(",").join(reverse), equalTo("3,2,1"));
        System.out.println(reverse);
    }

    @Test
    public void testPartition() {
        List<String> list = Lists.newArrayList("a","b","c","d");
        //分区，n个为一组
        List<List<String>> partition = Lists.partition(list, 3);
        System.out.println(partition);
    }

}
