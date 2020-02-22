package com.yejinhui.guava.utilities;

import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/8 13:59
 */
public class ElapsedExample {

    public static void main(String[] args) throws InterruptedException {
        process("42343254");
    }

    private static void process(String orderNo) throws InterruptedException {
        System.out.printf("start process the order %s\n", orderNo);
        long startNano = System.nanoTime();
        TimeUnit.SECONDS.sleep(1);

        System.out.printf("The orderNo %s process successful and elapsed %d ns.\n", orderNo, (System.nanoTime() - startNano));
    }
}
