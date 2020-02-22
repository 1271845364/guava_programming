package com.yejinhui.guava.concurrent;

import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.lang.Thread.currentThread;

/**
 * 锁和释放锁三种方式
 * 1、synchronized、wait()、notify()、notifyAll()
 * 2、ReentrantLock、Condition
 * 3、Monitor、Monitor.Guard
 *
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/14 22:04
 */
public class MonitorExample {

    public static void main(String[] args) {
        final MonitorGuard mg = new MonitorGuard();
        final AtomicInteger COUNTER = new AtomicInteger(0);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        int data = COUNTER.getAndIncrement();
                        mg.offer(data);
                        System.out.println(currentThread() + " offer " + data);
                        TimeUnit.MILLISECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        int data = mg.take();
                        System.out.println(currentThread() + " take " + data);
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    static class MonitorGuard {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        private final Monitor monitor = new Monitor();

        private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < MAX);
        private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

        public void offer(int value) {
            try {
                monitor.enterWhen(CAN_OFFER);
                queue.addLast(value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                monitor.leave();
            }
        }

        public int take() {
            Integer value = null;
            try {
                monitor.enterWhen(CAN_TAKE);
                value = queue.removeFirst();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                monitor.leave();
            }
            return value;
        }
    }

    static class LockCondition {
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition FULL_CONDITION = lock.newCondition();
        private final Condition EMPTY_CONDITION = lock.newCondition();
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            lockAndUnLock(v -> {
                while (queue.size() >= MAX) {
                    try {
                        FULL_CONDITION.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.addLast(v);
                EMPTY_CONDITION.signalAll();
            }, value);
        }

        public int take() {
            Integer value = lockAndUnLock(() -> {
                while (queue.isEmpty()) {
                    try {
                        EMPTY_CONDITION.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer removeFirst = queue.removeFirst();
                FULL_CONDITION.signalAll();
                return removeFirst;
            });
            return value;
        }

        /**
         * 封装下，别人直接调用，加锁和解锁，不用每个人都写，生产型
         *
         * @param supplier
         * @param <T>
         * @return
         */
        private <T> T lockAndUnLock(Supplier<T> supplier) {
            try {
                lock.lock();
                return supplier.get();
            } finally {
                lock.unlock();
            }
        }

        /**
         * 封装下，别人直接调用，加锁和解锁，不用每个人都写，消费型
         *
         * @param consumer
         * @param t
         * @param <T>
         */
        private <T> void lockAndUnLock(Consumer<T> consumer, T t) {
            try {
                lock.lock();
                consumer.accept(t);
            } finally {
                lock.unlock();
            }
        }
    }

    static class Synchronized {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int MAX = 10;

        public void offer(int value) {
            synchronized (queue) {
                while (queue.size() >= MAX) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                queue.addLast(value);
                queue.notifyAll();
            }
        }

        public Integer take() {
            synchronized (queue) {
                while (queue.isEmpty()) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer value = queue.removeFirst();
                queue.notifyAll();
                return value;
            }
        }
    }
}
