package com.yejinhui.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/9 17:09
 */
public class ByteSourceTest {

    private static final String path = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\Git.jpg";

    @Test
    public void testAsByteSource() throws IOException {
        File file = new File(path);
        ByteSource byteSource = Files.asByteSource(file);
        byte[] bytes = byteSource.read();
        assertThat(bytes, equalTo(Files.toByteArray(file)));
    }

    @Test
    public void testSliceByteSource() throws IOException {
        ByteSource byteSource = ByteSource.wrap(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
        //SliceByteSource
        ByteSource sliceByteSouce = byteSource.slice(5, 5);
        byte[] read = sliceByteSouce.read();
        for (byte b : read) {
            System.out.println(b);
        }
    }

}
