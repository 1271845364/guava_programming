package com.yejinhui.guava.eventbus.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 20:46
 */
public class DeadEventListener {

    @Subscribe
    public void handle(DeadEvent event) {
        System.out.println(event.getEvent());
        System.out.println(event.getSource());
    }
}
