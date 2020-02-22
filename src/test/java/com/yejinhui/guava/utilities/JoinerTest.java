package com.yejinhui.guava.utilities;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/3 21:35
 */
public class JoinerTest {

    private final String targetFileName = "E:\\video\\汪文君Google Guava实战视频\\code\\guava-joiner.txt";
    private final String targetFileNameToMap = "E:\\video\\汪文君Google Guava实战视频\\code\\guava-joiner-map.txt";

    private final Map<String, String> stringMap = ImmutableMap.of("Hello", "Guava", "Java", "Scala");

    private final List<String> stringList = Arrays.asList("Google", "Guava", "Java", "Scala", "Kafka");

    private final List<String> stringListWithNullValue = Arrays.asList("Google", "Guava", "Java", "Scala", null);

    @Test
    public void testJoinOnJoin() {
        String result = Joiner.on("#").join(stringList);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue() {
        String result = Joiner.on("#").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#Kafka"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButSkip() {
        String result = Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoin_On_Join_WithNullValue_UseDefaultValue() {
        String result = Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_StringBuilder() {
        final StringBuilder stringBuilder = new StringBuilder();
        StringBuilder resultBuilder = Joiner.on("#").useForNull("DEFAULT").appendTo(stringBuilder, stringListWithNullValue);
        assertThat(resultBuilder, sameInstance(stringBuilder));
        assertThat(resultBuilder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
        assertThat(stringBuilder.toString(), equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_Writer() {
        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT").appendTo(writer, stringListWithNullValue);
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fatal error.");
        }
    }

    @Test
    public void testJoiningByStreamSkipNullValues() {
        String result = stringListWithNullValue.stream().filter(item -> item != null && !item.isEmpty()).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoiningByStreamWithDefaultValue() {
        String result = stringListWithNullValue.stream().
//                map(item -> item == null || item.isEmpty() ? "DEFAULT" : item).collect(joining("#"));
        map(this::defaultValue).collect(joining("#"));
        assertThat(result, equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    private String defaultValue(final String item) {
        return item == null || item.isEmpty() ? "DEFAULT" : item;
    }

    @Test
    public void testJoinOnWithMap() {
        String result = Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        assertThat(result, equalTo("Hello=Guava#Java=Scala"));
    }

    @Test
    public void testJoinOnWithMapToAppendable() {
        Joiner.on("#").withKeyValueSeparator("=").join(stringMap);
        try (FileWriter writer = new FileWriter(new File(targetFileNameToMap))) {
            Joiner.on("#").withKeyValueSeparator("=").appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetFileNameToMap)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer occur fatal error.");
        }
    }
}
