package com.yejinhui.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 21:01
 */
public abstract class AbstractListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(AbstractListener.class);

    @Subscribe
    public void commonTask(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("The event [{}] will be handle by {}.{}", event, this.getClass().getSimpleName(), "commonTask");
        }
    }

}
