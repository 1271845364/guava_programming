package com.yejinhui.servant;

import com.yejinhui.service.Service;

import java.util.ServiceLoader;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/8 21:09
 */
public class ServiceInvoker {

    public static void main(String[] args) {
        ServiceLoader<Service> serviceLoader = ServiceLoader.load(Service.class);
        for (Service service : serviceLoader) {
            service.show();
        }
    }
}
