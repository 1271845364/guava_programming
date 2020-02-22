package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.yejinhui.guava.eventbus.listeners.SimpleListener;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 20:41
 */
public class SimpleEventBusExample {

    public static void main(String[] args) {
        //定义一个EventBus
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        eventBus.post("Simple Event");
    }
}
