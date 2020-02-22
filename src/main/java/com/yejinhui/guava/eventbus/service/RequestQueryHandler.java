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
 * @create 2020/2/13 21:12
 */
public class RequestQueryHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestQueryHandler.class);

    private final EventBus eventBus;

    public RequestQueryHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void doQuery(Request request) {
        LOGGER.info("start query the orderNo [{}]",request.toString());
        Response response = new Response();
        this.eventBus.post(response);
    }

}
