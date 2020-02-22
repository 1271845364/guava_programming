package com.yejinhui.guava.utilities;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/8 15:19
 */
public class StopWatchExample {

    private final static Logger LOGGER = LoggerFactory.getLogger(StopWatchExample.class);

    public static void main(String[] args) throws InterruptedException {
        process("42343254");
    }

    private static void process(String orderNo) throws InterruptedException {
        LOGGER.info("start process the order [{}]", orderNo);
        Stopwatch stopwatch = Stopwatch.createStarted();
        TimeUnit.MILLISECONDS.sleep(100);

        LOGGER.info("The orderNo [{}] process successful and elapsed [{}]", orderNo, stopwatch.stop());
    }
}
