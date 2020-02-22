package com.yejinhui.guava.eventbus.monitor;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 22:31
 */
public class FileChangeListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(FileChangeListener.class);

    @Subscribe
    public void onChange(FileChangeEvent fileChangeEvent) {
        LOGGER.info("{}-{}", fileChangeEvent.getPath(), fileChangeEvent.getKind());
    }
}
