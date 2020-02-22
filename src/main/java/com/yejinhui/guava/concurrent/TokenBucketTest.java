package com.yejinhui.guava.concurrent;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 14:49
 */
public class TokenBucketTest {

    public static void main(String[] args) {
        final TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 110; i++) {
            new Thread(tokenBucket::buy).start();
        }
    }
}
