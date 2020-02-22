package com.yejinhui.guava.collections;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/20 21:00
 */
public class RangeExampleTest {

    /**
     * {x|a<=x<=b}
     */
    @Test
    public void testClosedRange() {
        //类似IntStream
        Range<Integer> closedRange = Range.closed(0, 9);
//        IntStream.range()
        System.out.println(closedRange);

        assertThat(closedRange.contains(5), is(true));
        //range中最左边的点
        assertThat(closedRange.lowerEndpoint(), equalTo(0));
        assertThat(closedRange.upperEndpoint(), equalTo(9));
    }

    /**
     * {x|a<x<b}
     */
    @Test
    public void testOpen() {
        //类似IntStream
        Range<Integer> open = Range.open(0, 9);
//        IntStream.range()
        System.out.println(open);

        assertThat(open.contains(5), is(true));
        assertThat(open.contains(0), is(false));
        assertThat(open.contains(9), is(false));
        //range中最左边的点
        assertThat(open.lowerEndpoint(), equalTo(0));
        assertThat(open.upperEndpoint(), equalTo(9));
    }

    /**
     * openClosed = (a,b]
     * {x|a<x<=b}
     */
    @Test
    public void testOpenClosed() {
        //类似IntStream
        Range<Integer> open = Range.openClosed(0, 9);
//        IntStream.range()
        System.out.println(open);

        assertThat(open.contains(5), is(true));
        assertThat(open.contains(0), is(false));
        assertThat(open.contains(9), is(true));
        //range中最左边的点
        assertThat(open.lowerEndpoint(), equalTo(0));
        assertThat(open.upperEndpoint(), equalTo(9));
    }

    /**
     * closedOpen = [a,b)
     * {x|a<=x<b}
     */
    @Test
    public void testClosedOpen() {
        //类似IntStream
        Range<Integer> open = Range.closedOpen(0, 9);
//        IntStream.range()
        System.out.println(open);

        assertThat(open.contains(5), is(true));
        assertThat(open.contains(0), is(true));
        assertThat(open.contains(9), is(false));
        //range中最左边的点
        assertThat(open.lowerEndpoint(), equalTo(0));
        assertThat(open.upperEndpoint(), equalTo(9));
    }

    /**
     * {x|x>a}
     */
    @Test
    public void testGreaterThan() {
        Range<Integer> greaterThan = Range.greaterThan(10);
        assertThat(greaterThan.contains(10), is(false));
        assertThat(greaterThan.contains(Integer.MAX_VALUE), is(true));
    }

    @Test
    public void testMapRange() {
        TreeMap<String, Integer> treeMap = Maps.newTreeMap();
        treeMap.put("Scala", 1);
        treeMap.put("Guava", 2);
        treeMap.put("Java", 3);
        treeMap.put("Kafka", 4);
        System.out.println(treeMap);

        NavigableMap<String, Integer> result = Maps.subMap(treeMap, Range.openClosed("Guava", "Java"));
        System.out.println(result);
    }

    @Test
    public void testOtherMethod() {
        //[2..+∞)
        Range<Integer> range = Range.atLeast(2);
        System.out.println(range);
        assertThat(range.contains(2), is(true));

        //(-∞..10)
        System.out.println(Range.lessThan(10));
        System.out.println(Range.atMost(5));
        System.out.println(Range.all());

        System.out.println(Range.downTo(10, BoundType.CLOSED));
        System.out.println(Range.upTo(10, BoundType.CLOSED));
    }

    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> gradeScale = TreeRangeMap.<Integer, String>create();
        gradeScale.put(Range.closedOpen(0, 60), "E");
        gradeScale.put(Range.closedOpen(60, 70), "D");
        gradeScale.put(Range.closedOpen(70, 80), "C");
        gradeScale.put(Range.closedOpen(80, 90), "B");
        gradeScale.put(Range.closed(90, 100), "A");
        System.out.println(gradeScale);

        System.out.println(gradeScale.get(77));
        assertThat(gradeScale.get(77), equalTo("C"));
    }
}
