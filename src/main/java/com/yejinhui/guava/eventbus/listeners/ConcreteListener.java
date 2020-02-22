package com.yejinhui.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 21:09
 */
public class ConcreteListener extends BaseListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(ConcreteListener.class);

    @Subscribe
    public void conTask(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("The event [{}] will be handle by {}.{}", event, this.getClass().getSimpleName(), "conTask");
        }
    }

}
