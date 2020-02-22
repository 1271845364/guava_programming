package com.yejinhui.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/18 21:18
 */
public class FluentIterableExampleTest {

    @Test
    public void testFilter() {
        FluentIterable<String> fluentIterable = build();
        //加工之前和之后都做一次断言
        assertThat(fluentIterable.size(), equalTo(4));
        FluentIterable<String> result = fluentIterable.filter(e -> e.length() > 4);
        assertThat(result.size(), equalTo(2));
    }

    @Test
    public void testAppend() {
        FluentIterable<String> fluentIterable = build();
        assertThat(fluentIterable.size(), equalTo(4));
        ArrayList<String> append = Lists.newArrayList("APPEND");
        FluentIterable<String> result = fluentIterable.append(append);
        assertThat(result.size(), equalTo(5));
        assertThat(result.contains("APPEND"), equalTo(true));

        result = result.append("APPEND2");
        assertThat(result.size(), equalTo(6));
        assertThat(result.contains("APPEND2"), equalTo(true));
    }

    @Test
    public void testMatch() {
        FluentIterable<String> build = build();
        boolean result = build.allMatch(e -> e != null && e.length() >= 4);
        assertThat(result, is(true));
        result = build.anyMatch((e -> e.length() == 5));
        assertThat(result, is(true));
        Optional<String> stringOptional = build.firstMatch(e -> e.length() == 5);
        assertThat(stringOptional.isPresent(), is(true));
        assertThat(stringOptional.get(), equalTo("Guava"));
    }

    @Test
    public void testFirst$Last() {
        FluentIterable<String> build = build();
        Optional<String> stringOptional = build.first();
        assertThat(stringOptional.isPresent(), is(true));
        assertThat(stringOptional.get(), equalTo("Alex"));

        stringOptional = build.last();
        assertThat(stringOptional.isPresent(), is(true));
        assertThat(stringOptional.get(), equalTo("Scala"));
    }

    @Test
    public void testLimit() {
        FluentIterable<String> build = build();
        FluentIterable<String> limit = build.limit(300);
        System.out.println(limit);
        assertThat(limit.contains("Scala"), is(true));
    }

    /**
     * DSL编码风格
     */
    @Test
    public void testCopyInto() {
        FluentIterable<String> build = build();
        ArrayList<String> list = Lists.newArrayList("Java");
        build.copyInto(list);
        assertThat(list.size(), equalTo(5));
        assertThat(list.contains("Scala"), equalTo(true));
    }

    /**
     * Cycle就是循环
     */
    @Test
    public void testCycle() {
        FluentIterable<String> build = build();
        FluentIterable<String> cycle = build.cycle().limit(20);
        cycle.forEach(System.out::println);
    }

    @Test
    public void testTransform() {
        FluentIterable<String> build = build();
        build.transform(e -> e.length()).forEach(System.out::println);
    }

    @Test
    public void testTransformContact() {
        FluentIterable<String> build = build();
        List<Integer> list = Lists.newArrayList(1);
        FluentIterable<Object> result = build.transformAndConcat(e -> list);
        result.forEach(System.out::println);
    }

    @Test
    public void testTransformAndContactInAction() {
        ArrayList<Integer> cType = Lists.newArrayList(1, 2);
        //转换完了再contact
        FluentIterable<Customer> customers = FluentIterable.from(cType).transformAndConcat(c -> search(c));
        customers.forEach(System.out::println);
    }

    @Test
    public void testJoin() {
        FluentIterable<String> build = build();
        String join = build.join(Joiner.on(","));
        System.out.println(join);
        assertThat(join, equalTo("Alex,Wang,Guava,Scala"));
    }

    private List<Customer> search(int type) {
        if (type == 1) {
            return Lists.newArrayList(new Customer(type, "Alex"),
                    new Customer(type, "Tina"));
        }
        return Lists.newArrayList(new Customer(type, "Wang"),
                new Customer(type, "Wen"), new Customer(type, "Jun"));
    }

    class Customer {
        private int type;
        private String name;

        public Customer(int type, String name) {
            this.type = type;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "type=" + type +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private FluentIterable<String> build() {
        ArrayList<String> list = Lists.newArrayList("Alex", "Wang", "Guava", "Scala");
        return FluentIterable.from(list);
    }

}
