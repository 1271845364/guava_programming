package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.yejinhui.guava.eventbus.listeners.ExceptionListener;

import javax.naming.Context;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 22:43
 */
public class ExceptionEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus((exception, context) -> {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        });
        eventBus.register(new ExceptionListener());

        eventBus.post("exception post");
    }

    static class ExceptionHandler implements SubscriberExceptionHandler {

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        }
    }
}
