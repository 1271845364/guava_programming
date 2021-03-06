package com.yejinhui.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/9 15:27
 */
public class CharSourceTest {

    @Test
    public void testCharSourceWrap() throws IOException {
        CharSource charSource = CharSource.wrap("i am the CharSource");
        String resultAsRead = charSource.read();
        assertThat(resultAsRead, equalTo("i am the CharSource"));

        ImmutableList<String> immutableList = charSource.readLines();
        assertThat(immutableList.size(), equalTo(1));

        assertThat(charSource.length(), equalTo(19L));
        assertThat(charSource.isEmpty(), equalTo(false));

        assertThat(charSource.lengthIfKnown().get(), equalTo(19L));
    }

    @Test
    public void testConcat() throws IOException {
        CharSource charSource = CharSource.concat(
                CharSource.wrap("i am the CharSource1\n"),
                CharSource.wrap("i am the CharSource2")
        );
        System.out.println(charSource.readLines().size());
        charSource.readLines().stream().forEach(System.out::println);
    }

}
