package com.yejinhui.guava.eventbus;

import com.google.common.eventbus.EventBus;
import com.yejinhui.guava.eventbus.service.QueryService;
import com.yejinhui.guava.eventbus.service.RequestQueryHandler;

/**
 * 通信的方式通过EventBus交互
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 21:16
 */
public class ComEachOtherEventBusExample {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        QueryService queryService = new QueryService(eventBus);
        eventBus.register(new RequestQueryHandler(eventBus));
        queryService.service("fsfeweacdsce");

    }
}
