package com.yejinhui.guava.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * 漏桶限流
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 13:47
 */
public class BucketTest {

    public static void main(String[] args) {
        final Bucket bucket = new Bucket();
        final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

        //五个线程，每个线程不断的塞数据
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        bucket.submit(DATA_CREATOR.getAndIncrement());
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (Exception e) {
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });//插入数据25/s

        //限流。消费速度10/s
        IntStream.range(0, 5).forEach(i -> {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        bucket.takeThenConsume(x -> {
                            System.out.println(currentThread() + " W " + x);
                        });
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (Exception e) {
                        if (e instanceof IllegalStateException) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }).start();
        });
    }
}
