package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.yejinhui.guava.eventbus.events.Apple;
import com.yejinhui.guava.eventbus.events.Fruit;
import com.yejinhui.guava.eventbus.listeners.ConcreteListener;
import com.yejinhui.guava.eventbus.listeners.FruitEaterListener;

/**
 * 类继承之间的EventBus
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 20:55
 */
public class InheritEventEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new FruitEaterListener());
        eventBus.post(new Apple("apple"));
        System.out.println("======================");
        eventBus.post(new Fruit("apple"));
    }

}
