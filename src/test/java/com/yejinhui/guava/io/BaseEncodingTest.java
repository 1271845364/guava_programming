package com.yejinhui.guava.io;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/9 21:33
 */
public class BaseEncodingTest {

    @Test
    public void testBase64Encode() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(baseEncoding.encode("Hello".getBytes()));
    }

    @Test
    public void testBase64Decode() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        System.out.println(new String(baseEncoding.decode("SGVsbG8=")));
    }

    @Test
    public void testMyBase64Encode() {
        assertThat(Base64.encode("hello"), equalTo(BaseEncoding.base64().encode("hello".getBytes())));
        assertThat(Base64.encode("java"), equalTo(BaseEncoding.base64().encode("java".getBytes())));
        assertThat(Base64.encode("scala"), equalTo(BaseEncoding.base64().encode("scala".getBytes())));
    }

}
