package com.yejinhui.guava.eventbus.listeners;

import com.google.common.eventbus.Subscribe;
import com.yejinhui.guava.eventbus.events.Apple;
import com.yejinhui.guava.eventbus.events.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 21:20
 */
public class FruitEaterListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(FruitEaterListener.class);

    @Subscribe
    public void eat(Fruit event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Fruit eat [{}].", event);
        }
    }

    @Subscribe
    public void eat(Apple event) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Apple eat [{}].", event);
        }
    }

}
