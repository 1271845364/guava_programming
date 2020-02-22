package com.yejinhui.guava.eventbus.monitor;

/**
 * 监控本地磁盘的一个目录如果里面发生变化就通知
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 21:28
 */
public interface TargetMonitor {

    void startMonitor() throws Exception;

    void stopMonitor() throws Exception;
}
