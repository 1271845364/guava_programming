package com.yejinhui.guava.cache;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/15 21:30
 */
public class ReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        //Strong Reference
//        int counter = 1;
//        //将创建的对象保存起来，为了触发GC
//        List<Ref> container = new ArrayList<>();
//
//        for (; ; ) {
//            int current = counter++;
//            container.add(new Ref(current));
//            System.out.println("The " + current + " Ref will be insert into container.");
//            TimeUnit.MILLISECONDS.sleep(500);
//        }


        //SoftReference  在JVM可能判断将要OOM的时候会去尝试GC是否有soft reference，如果有直接回收，而WeakReference更脆弱
        //基本上会用SoftReference做cache，读次数 >> 写次数
//        SoftReference<Ref> reference = new SoftReference<>(new Ref(0));
//        int counter = 1;
//        //将创建的对象保存起来，为了触发GC
//        List<SoftReference<Ref>> container = new ArrayList<>();
//
//        for (; ; ) {
//            int current = counter++;
//            container.add(new SoftReference(new Ref(current)));
//            System.out.println("The " + current + " Ref will be insert into container.");
//            TimeUnit.SECONDS.sleep(1);
//        }


        //Weak referencce，The reference will be collected when GC.
//        int counter = 1;
//        //将创建的对象保存起来，为了触发GC
//        List<WeakReference<Ref>> container = new ArrayList<>();
//
//        for (; ; ) {
//            int current = counter++;
//            container.add(new WeakReference<>(new Ref(current)));
//            System.out.println("The " + current + " Ref will be insert into container.");
//            //GC的线程是守护线程并且优先级是非常低的
//            TimeUnit.MILLISECONDS.sleep(200);
//        }

        //PhantomReference
//        Ref ref = new Ref(10);
//        ReferenceQueue<?> queue = new ReferenceQueue();
//        //幻影，可以放进去，不能拿出来
//        PhantomReference<Ref> reference = new PhantomReference(ref, queue);
//        ref = null;
//
//        System.out.println(reference.get());
//
//        System.gc();
//        TimeUnit.SECONDS.sleep(1);
//
//        Reference<?> object = queue.remove();
//        System.out.println(object);


        Ref ref = new Ref(10);
        ReferenceQueue<?> queue = new ReferenceQueue();
        //幻影，可以放进去，不能拿出来
        MyPhantomReference reference = new MyPhantomReference(ref, queue,10);
        ref = null;

        System.out.println(reference.get());

        System.gc();
        TimeUnit.SECONDS.sleep(1);

        Reference<?> object = queue.remove();
        ((MyPhantomReference)object).doAction();

    }

    private static class MyPhantomReference extends PhantomReference {

        private final int index;

        public MyPhantomReference(Object referent, ReferenceQueue q, int index) {
            super(referent, q);
            this.index = index;
        }

        public void doAction() {
            System.out.println("The object " + index + " is GC.");
        }
    }

    private static class Ref {

        //1M
        private byte[] data = new byte[1024 * 1024];

        private final int index;

        public Ref(int index) {
            this.index = index;
        }

        /**
         * JVM回收对象的时候，会调用finalize()给对象打个标记，这个方法只会调用一次
         *
         * @throws Throwable
         */
        @Override
        protected void finalize() throws Throwable {
            System.out.println("The index [" + index + "] will be GC.");
        }
    }

}
