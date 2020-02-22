package com.yejinhui.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 10:36
 */
public class RateLimiterExample {

    //在1s可以有0.5个结果，可以是1个或n个线程同时访问
    private static final RateLimiter LIMITER = RateLimiter.create(0.5d);

    //同一个时刻只能有三个线程获取许可
    private static final Semaphore SEMAPHORE = new Semaphore(3);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).forEach(i -> {
//            executorService.submit(RateLimiterExample::testLimiter);
            executorService.submit(RateLimiterExample::testSemaphore);
        });
    }

    private static void testLimiter() {
        System.out.println(currentThread() + " waiting " + LIMITER.acquire());
    }

    private static void testSemaphore() {
        try {
            SEMAPHORE.acquire();
            System.out.println(currentThread() + " is coming and do work.");
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            SEMAPHORE.release();
            System.out.println(currentThread() + " release the semaphore.");
        }
    }
}
