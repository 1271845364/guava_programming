package com.yejinhui.guava.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/20 20:37
 */
public class TableExampleTest {

    /**
     * 一共四种table：类似数据库的表
     * ArrayTable
     * TreeBaseTable
     * HashBaseTable
     * ImmutableTable
     */

    @Test
    public void test() {
        //Table结构类似 Map<String,Map<String,String>>
        Table<String, String, String> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Scala", "2.3");
        table.put("Database", "Oracle", "12C");
        table.put("Database", "Mysql", "7.0");
        System.out.println(table);

        Map<String, String> language = table.row("Language");
        assertThat(language.containsKey("Java"), is(true));

        assertThat(table.row("Language").get("Java"), equalTo("1.8"));

        Map<String, String> java = table.column("Java");
        System.out.println(java);

        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);
    }

}
