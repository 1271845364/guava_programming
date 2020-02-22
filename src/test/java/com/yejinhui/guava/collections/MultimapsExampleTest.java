package com.yejinhui.guava.collections;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimaps;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/19 22:33
 */
public class MultimapsExampleTest {

    /**
     * LinkedHashMultimap类似Map<String,Set<Object>>结构
     */
    @Test
    public void test() {
        LinkedHashMultimap<String, String> linkedHashMultimap = LinkedHashMultimap.create();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "1");
        hashMap.put("1", "2");
        assertThat(hashMap.size(), equalTo(1));

        linkedHashMultimap.put("1","1");
        linkedHashMultimap.put("1","1");
        linkedHashMultimap.put("1","2");
        assertThat(linkedHashMultimap.size(), equalTo(2));
        System.out.println(linkedHashMultimap);

        System.out.println(linkedHashMultimap.get("1"));
    }

}
