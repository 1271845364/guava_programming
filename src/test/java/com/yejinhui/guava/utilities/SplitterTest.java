package com.yejinhui.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/6 22:04
 */
public class SplitterTest {

    @Test
    public void testSplitOnSplit() {
        List<String> list = Splitter.on("|").splitToList("hello|world");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0), equalTo("hello"));
        assertThat(list.get(1), equalTo("world"));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty() {
        List<String> list = Splitter.on("|").splitToList("hello|world|||");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(5));

        list = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|||");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(2));
    }

    @Test
    public void testSplit_On_Split_OmitEmpty_TrimResult() {
        List<String> list = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|||");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0), equalTo("hello "));
        assertThat(list.get(1), equalTo(" world"));

        list = Splitter.on("|").omitEmptyStrings().trimResults().splitToList("hello | world|||");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0), equalTo("hello"));
        assertThat(list.get(1), equalTo("world"));
    }

    /**
     * aaaabbbbccccdddd
     */
    @Test
    public void testSplitFixLength() {
        List<String> list = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(4));
        assertThat(list.get(0), equalTo("aaaa"));
        assertThat(list.get(3), equalTo("dddd"));
    }

    @Test
    public void testSplitOnSplitLimit() {
        List<String> list = Splitter.on("#").limit(3).splitToList("hello#world#java#google#scala");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(3));
        assertThat(list.get(0), equalTo("hello"));
        assertThat(list.get(2), equalTo("java#google#scala"));
    }

    @Test
    public void testSplitOnPattern() {
//        List<String> list = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world|||");
        List<String> list = Splitter.on(
                Pattern.compile("\\|")
        ).trimResults().omitEmptyStrings().splitToList("hello | world|||");
        assertThat(list, notNullValue());
        assertThat(list.size(), equalTo(2));
        assertThat(list.get(0), equalTo("hello"));
        assertThat(list.get(1), equalTo("world"));
    }

    @Test
    public void testSplitOnSplitToMap() {
        Map<String, String> map = Splitter.on("|").trimResults().omitEmptyStrings().
                withKeyValueSeparator("=").split("hello=HELLO | world=WORLD|||");
        assertThat(map,notNullValue());
        assertThat(map.size(),equalTo(2));
        assertThat(map.get("hello"),equalTo("HELLO"));
        assertThat(map.get("world"),equalTo("WORLD"));
    }
}
