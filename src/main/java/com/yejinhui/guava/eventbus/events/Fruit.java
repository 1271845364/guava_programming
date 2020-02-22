package com.yejinhui.guava.eventbus.events;

import com.google.common.base.MoreObjects;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/12 21:17
 */
public class Fruit {

    private String name;

    public Fruit(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Name",name).toString();
    }
}
