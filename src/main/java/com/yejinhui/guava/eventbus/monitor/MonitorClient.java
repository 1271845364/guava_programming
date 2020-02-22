package com.yejinhui.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 22:40
 */
public class MonitorClient {

    public static void main(String[] args) throws Exception {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FileChangeListener());
        TargetMonitor monitor = new DirectoryTargetMonitor(eventBus, "E:\\monitor");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.schedule(() -> {
                    try {
                        monitor.stopMonitor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                , 2, TimeUnit.SECONDS);
        scheduledExecutorService.shutdown();
        monitor.startMonitor();

    }
}
