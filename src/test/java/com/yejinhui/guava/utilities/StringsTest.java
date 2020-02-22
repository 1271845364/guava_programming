package com.yejinhui.guava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/7 23:10
 */
public class StringsTest {

    @Test
    public void testStringsMethod() {
        //空字符串转成null
        assertThat(Strings.emptyToNull(""), nullValue());
        assertThat(Strings.nullToEmpty(null), equalTo(""));
        assertThat(Strings.commonPrefix("Helo", "Hit"), equalTo("H"));
        assertThat(Strings.commonPrefix("Helo", "Xit"), equalTo(""));
        assertThat(Strings.commonSuffix("Helo", "Echo"), equalTo("o"));
        assertThat(Strings.repeat("Alex", 3), equalTo("AlexAlexAlex"));
        assertThat(Strings.isNullOrEmpty(null), equalTo(true));
        assertThat(Strings.isNullOrEmpty(""), equalTo(true));

        assertThat(Strings.padStart("Alex", 3, 'H'), equalTo("Alex"));
        assertThat(Strings.padStart("Alex", 5, 'H'), equalTo("HAlex"));
        assertThat(Strings.padEnd("Alex", 5, 'H'), equalTo("AlexH"));
    }

    @Test
    public void testCharsets() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(Charsets.UTF_8, equalTo(charset));
    }

    @Test
    public void testCharMatcher() {
        assertThat(CharMatcher.javaDigit().matches('5'), equalTo(true));
        assertThat(CharMatcher.javaDigit().matches('x'), equalTo(false));

        //字符在字符串中出现的次数
        assertThat(CharMatcher.is('A').countIn("Alex sharing the google Guava to us"), equalTo(1));

        assertThat(CharMatcher.breakingWhitespace().collapseFrom("    hello  Guava   ", '*'),
                equalTo("*hello*Guava*"));

        //数字和空格移除
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).removeFrom("hello 234 world")
                , equalTo("helloworld"));
        //保留数字和空格
        assertThat(CharMatcher.javaDigit().or(CharMatcher.whitespace()).retainFrom("hello 234 world ")
                , equalTo(" 234  "));
    }
}
