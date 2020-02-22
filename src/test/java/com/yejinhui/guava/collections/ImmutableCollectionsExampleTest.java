package com.yejinhui.guava.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/20 22:33
 */
public class ImmutableCollectionsExampleTest {

    @Test
    public void test() {
        //不可变list，多线程访问不可变的更安全，不可变 = 不可以update操作
        ImmutableList<Integer> immutableList = ImmutableList.of(1, 2, 3);
        assertThat(immutableList, notNullValue());
        System.out.println(immutableList);
        try {
            immutableList.add(23);
            fail();
        } catch (Exception e) {
            if (e instanceof UnsupportedOperationException) {
            }
        }
    }

    @Test
    public void testCopy() {
        Integer[] array = {1, 2, 3, 4, 5};
        ImmutableList<Integer> immutableList = ImmutableList.copyOf(array);
        System.out.println(immutableList);
    }

    @Test
    public void testBuilder() {
        ImmutableList<Integer> list = ImmutableList.<Integer>builder()
                .add(1)
                .add(2, 3, 4)
                .addAll(Arrays.asList(5, 6))
                .build();
        System.out.println(list);
    }

    @Test
    public void testImmutableMap() {
        ImmutableMap<String, String> builder = ImmutableMap.<String, String>builder()
                .put("Oracle", "12C")
                .put("Mysql", "7.0")
                .build();
        System.out.println(builder);
        try {
            builder.put("Scala", "2.3");
            fail();
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(UnsupportedOperationException.class));
        }
    }
}
