package com.yejinhui.guava.concurrent;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 15:41
 */
public class ListenableFutureExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        //java7以前的解决方式，block方式
//        Future<Integer> future = service.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 10;
//        });
//
//        //主动拿结果，需要block；能不能是被动的等待通知我，类似callback
//        Integer result = future.get();
//        System.out.println(result);
//        System.out.println("#########################");


        //guava的callback的方式
//        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(service);
//        ListenableFuture<Integer> future = listeningExecutorService.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 100;
//        });
//
////        future.addListener(() -> {
////            System.out.println("I am finished");
////        }, service);
//
//        Futures.addCallback(future, new MyCallBack(), service);
//        System.out.println("=============================");


        //java8的解决方式，非block，如果自己不提供线程池，里面的线程池的线程都是守护线程
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        },service);

        completableFuture.whenComplete((v, e) -> {
            System.out.println("I am finished and the result is " + v);
        });
        System.out.println("================");
    }

    static class MyCallBack implements FutureCallback<Integer> {

        @Override
        public void onSuccess(Integer result) {
            System.out.println("I am finished and the result is " + result);
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
        }
    }
}
