package com.yejinhui.guava.cache;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/3/30 10:40
 */
public class ReferencceTest {

    /**
     * java.lang.OutOfMemoryError: Requested array size exceeds VM limit
     * <p>
     * at com.yejinhui.guava.cache.ReferencceTest.method1(ReferencceTest.java:20)
     * <p>
     * 当内存不足的时候，jvm也不会回收这种强引用对象
     */
    @Test
    public void testStrongReference() {
        method1();
    }

    public void method1() {
        Object object = new Object();
        Object[] objArr = new Object[Integer.MAX_VALUE];
    }

    /**
     * 软引用
     * 只有在内存不足的时候jvm才会回收该对象，可以优雅的解决oom问题，适合做缓存
     */
    @Test
    public void testSoftReference() {
        System.out.println("start");
        Obj obj = new Obj();
        SoftReference<Obj> sr = new SoftReference<>(obj);
        obj = null;
        System.out.println(sr.get());//com.yejinhui.guava.cache.Obj@5b275dab
        System.out.println("end");
    }

    /**
     * 弱引用
     * 非必须对象，jvm回收时候，无论内存是否充足，都会回收被弱引用关联的对象
     * 弱引用对象在jvm进行垃圾回收的时候总会被回收
     * 由于垃圾收集器是一个优先级很低的线程，因此不一定会很快的发现那些只具有弱引用的对象
     */
    @Test
    public void testWeakReference() {
        WeakReference<String> str = new WeakReference<>(new String("hello"));
        System.out.println(str.get());
        System.gc();//通知jvm的gc进行垃圾回收
        System.out.println(str.get());//null

        Object o = new Object(); //只要o还指向对象就不会被回收
        WeakReference<Object> wr = new WeakReference<Object>(o);
        System.out.println(wr.get());
        o = null;
        System.gc();
        System.out.println(wr.get());
    }

    /**
     * 虚引用在任何时候都可能被垃圾回收器回收
     * 虚引用必须和引用队列关联使用
     */
    @Test
    public void testPhantomReference() {
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        String hello = new String("hello");
        PhantomReference<String> pr = new PhantomReference<>(hello, queue);
        System.out.println(pr.get());
    }

}

class Obj {
    int[] obj;

    public Obj() {
        obj = new int[1000];
    }
}
