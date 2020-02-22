package com.yejinhui.guava.eventbus.events;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/13 21:04
 */
public class Request {

    private final String orderNo;

    public Request(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "Request{" +
                "orderNo='" + orderNo + '\'' +
                '}';
    }
}
