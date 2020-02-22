package com.yejinhui.guava.concurrent;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.sql.Time;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

/**
 * 令牌桶
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 14:23
 */
public class TokenBucket {

    private AtomicInteger phoneNumbers = new AtomicInteger(0);

    private final static int LIMIT = 100;

    /**
     * 1s只能有10个人抢到
     */
    private RateLimiter rateLimiter = RateLimiter.create(10);

    private final int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int saleLimit) {
        this.saleLimit = saleLimit;
    }

    public int buy() {
        Stopwatch started = Stopwatch.createStarted();
        //抢的时候，等待10s
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.SECONDS);
        if (success) {
            int phoneNo = phoneNumbers.getAndIncrement();
            if (phoneNo >= saleLimit) {
                throw new IllegalStateException("Not any phone can be sale,please wait to next time.");
            }
            handleOrder();
            System.out.println(currentThread() + "user get the Mi phone:" + phoneNo + ",ETL:" + started.stop());
            return phoneNo;
        } else {
            started.stop();
            throw new RuntimeException("Sorry,occur exception when buy phone");
        }
    }

    private void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
