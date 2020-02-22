package com.yejinhui.guava.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * 漏桶限流
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 12:29
 */
public class Bucket {

    private final ConcurrentLinkedQueue<Integer> container = new ConcurrentLinkedQueue<>();

    private final static int BUCKET_LIMIT = 1000;

    /**
     * 1s我就处理10个，处理一个要0.1s左右
     */
    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor offerMonitor = new Monitor();

    private final Monitor pollMonitor = new Monitor();

    public void submit(Integer data) {
        if (offerMonitor.enterIf(offerMonitor.newGuard(() -> container.size() < BUCKET_LIMIT))) {
            try {
                container.offer(data);
                System.out.println(currentThread() + " submit data " + data + ",current size:" + container.size());
            } finally {
                offerMonitor.leave();
            }
        } else {
            //降级
            throw new IllegalStateException("The bucket is full.");
        }
    }

    /**
     * 多个线程会来访问，Monitor要离开
     */
    public void takeThenConsume(Consumer<Integer> consumer) {
        //container有数据就获取锁
        if (pollMonitor.enterIf(pollMonitor.newGuard(() -> !container.isEmpty()))) {
            try {
                System.out.println(currentThread() + " waiting " + limiter.acquire());
                consumer.accept(container.poll());
            } finally {
                pollMonitor.leave();
            }
        }
    }

}
