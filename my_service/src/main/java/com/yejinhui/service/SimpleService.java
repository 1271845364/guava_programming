package com.yejinhui.service;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/8 18:28
 */
public class SimpleService implements Service {
    @Override
    public void show() {
        System.out.println("hi i come from the service loader.");
    }
}
