package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.yejinhui.guava.eventbus.listeners.ConcreteListener;
import com.yejinhui.guava.eventbus.listeners.MultipleEventListeners;

/**
 * 类继承之间的EventBus
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 20:55
 */
public class InheritListenerEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new ConcreteListener());
        System.out.println("post the string event");
        eventBus.post("I am string event");
        System.out.println("post the int event");
        eventBus.post(1000);
    }

}
