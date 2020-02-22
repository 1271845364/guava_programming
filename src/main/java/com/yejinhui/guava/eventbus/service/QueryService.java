package com.yejinhui.guava.eventbus.service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.yejinhui.guava.eventbus.events.Request;
import com.yejinhui.guava.eventbus.events.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 21:07
 */
public class QueryService {

    public static final Logger LOGGER = LoggerFactory.getLogger(QueryService.class);

    private final EventBus eventBus;

    public QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public void service(String orderNo) {
        LOGGER.info("Received the orderNo [{}]", orderNo);
        //专门去查，让他去干
        this.eventBus.post(new Request(orderNo));
    }

    @Subscribe
    public void handleResponse(Response response) {
        LOGGER.info("{}", response);
    }
}
