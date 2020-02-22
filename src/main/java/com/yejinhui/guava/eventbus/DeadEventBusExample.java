package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.yejinhui.guava.eventbus.listeners.DeadEventListener;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 20:47
 */
public class DeadEventBusExample {

    public static void main(String[] args) {
        final DeadEventListener deadEventListener = new DeadEventListener();
        final EventBus eventBus = new EventBus("DeadEventBus") {
            @Override
            public String toString() {
                return "DEAD-EVENT-BUS";
            }
        };
        eventBus.register(deadEventListener);

        eventBus.post("Hello");
        eventBus.post("Hello");

        eventBus.unregister(deadEventListener);
        eventBus.post("Hello");
        eventBus.post("Hello");
        eventBus.post("Hello");
        eventBus.post("Hello");
        eventBus.post("Hello");
        eventBus.post("Hello");

    }
}
