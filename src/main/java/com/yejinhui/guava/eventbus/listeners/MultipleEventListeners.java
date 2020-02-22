package com.yejinhui.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 20:45
 */
public class MultipleEventListeners {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    @Subscribe
    public void task1(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("the event [{}] received and will take a action by --task1--", event);
        }
    }

    @Subscribe
    public void task2(final String event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("the event [{}] received and will take a action by --task2--", event);
        }
    }

    @Subscribe
    public void intTask(final Integer event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("the event [{}] received and will take a action by --intTask--", event);
        }
    }
}
