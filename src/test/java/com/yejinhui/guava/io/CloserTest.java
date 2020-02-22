package com.yejinhui.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/9 20:37
 */
public class CloserTest {

    @Test
    public void testCloser() throws IOException {
    }

    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        try {
            System.out.println("work area.");
            throw new IllegalArgumentException();
        } catch (Exception e) {
            System.out.println("exception area.");
            int i = 1 / 0;
        } finally {
            System.out.println("finally area.");
        }
    }

    /**
     * finally中出现了异常，把try中的异常给压制了
     */
    @Test
    public void testTryCatch() {
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            throw e;
        } finally {
            throw new RuntimeException("2");
        }
    }

    @Test
    public void testTryCatch2() {
        Throwable t = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            RuntimeException runtimeException = new RuntimeException("2");
            //抛出runtimeException的时候顺便可以把t也带出来
            runtimeException.addSuppressed(t);
            throw runtimeException;
        }
    }

    /**
     * finally中的异常有可能影响真正catch的异常
     */
    @Test
    public void testTryCatch3() {
        Throwable t = null;
        try {
            throw new RuntimeException("1");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            try {
                throw new RuntimeException("2");
            } catch (Exception e) {
                //把e压到t底下，先抛出t
                t.addSuppressed(e);
            }
        }
    }

}
