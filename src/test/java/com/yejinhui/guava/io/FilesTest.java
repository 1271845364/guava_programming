package com.yejinhui.guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/8 21:47
 */
public class FilesTest {

    private final String SOURCE_FILE = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\source.txt";
    private final String TARGET_FILE = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\target.txt";

    /**
     * @throws IOException
     */
    @Test
    public void testCopyFileWithGuava() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        Files.copy(sourceFile, targetFile);

        assertThat(targetFile.isFile(), equalTo(true));
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        assertThat(targetHashCode, equalTo(sourceHashCode));
    }

    @Test
    public void testCopyFileWithJDKNio2() throws IOException {
        java.nio.file.Files.copy(Paths.get("E:\\study-idea-workspace\\guava_programming\\src\\test\\resources", "io", "source.txt"),
                Paths.get("E:\\study-idea-workspace\\guava_programming\\src\\test\\resources", "io", "target.txt"),
                StandardCopyOption.REPLACE_EXISTING);
        File targetFile = new File(TARGET_FILE);
        assertThat(targetFile.isFile(), equalTo(true));
    }

    @Test
    public void testMoveFile() throws IOException {
        try {
            Files.move(new File(SOURCE_FILE), new File(TARGET_FILE));
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
            assertThat(new File(TARGET_FILE).exists(), equalTo(true));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }
    }

    @Test
    public void testToString() throws IOException {
        final String expectedString = "today we will share the guava io knowlege.\n" +
                "but only for the basic usage.if you wanted to get the more details information\n" +
                "please read the guava document or source code.\n" +
                "\n" +
                "The guava source code is very cleanly and nice.";
        List<String> list = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String join = Joiner.on("\n").join(list);
        assertThat(join, equalTo(expectedString));
    }

    @Test
    public void testToProcessString() throws IOException {

        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {
            private final List<Integer> list = new ArrayList<>();

            /**
             * 此方法为如果这行数据要，返回true；返回false，后面的就不会继续处理了，源码中直接break了
             * [42, 78, 46, 0, 47]
             *
             * @param line
             * @return
             * @throws IOException
             */
            @Override
            public boolean processLine(String line) throws IOException {
                if (line.length() == 0) return false;
                list.add(line.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return list;
            }
        };

        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);
        System.out.println(result);
    }

    @Test
    public void testFileSha() throws IOException {
        File file = new File(SOURCE_FILE);
        Files.hash(file, Hashing.goodFastHash(128));
        HashCode hash = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hash);
    }

    @Test
    public void testFileWrite() throws IOException {
        String testPath = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        String content1 = "content1";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content1);
        String actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content1));

        String content2 = "content2";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content2);
        actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content2));

        testFile.deleteOnExit();
    }

    @Test
    public void testFileAppend() throws IOException {
        String testPath = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\testFileWrite.txt";
        File testFile = new File(testPath);
        String content1 = "content1";
        CharSink charSink = Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write(content1);

        CharSource charSource = Files.asCharSource(testFile, Charsets.UTF_8);
        String actually = charSource.read();
        assertThat(actually, equalTo(content1));

        String content2 = "content2";
        charSink.write(content2);//append
        actually = charSource.read();
        assertThat(actually, equalTo("content1content2"));

        testFile.deleteOnExit();
    }

    @Test
    public void testTouchFile() throws IOException {
        File touchFile = new File("E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\touch.txt");
        touchFile.deleteOnExit();//在退出的时候删除
        Files.touch(touchFile);

        assertThat(touchFile.exists(), equalTo(true));
    }

    @Test
    public void testRecursive() {
        List<File> fileList = new ArrayList<>();
        File file = new File("E:\\study-idea-workspace\\guava_programming\\src\\main");
        recursiveList(file, fileList);
        fileList.stream().forEach(System.out::println);
    }

    private void recursiveList(File root, List<File> fileList) {
        //fileList存放文件和文件夹
//        if (root.isHidden()) {
//            return;
//        }
//        fileList.add(root);
//        if (root.isDirectory()) {
//            File[] files = root.listFiles();
//            Arrays.stream(files).forEach(f -> recursiveList(f, fileList));
//        } else {
//            root.delete();
//        }

        if (root.isHidden()) {
            return;
        }
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            Arrays.stream(files).forEach(file -> recursiveList(file, fileList));
        } else {
            fileList.add(root);
        }
    }

    @Test
    public void testTreeFilesPreOrderTraversal() {
        File root = new File("E:\\study-idea-workspace\\guava_programming\\src\\main");
        //保留之前的顺序文件和目录
        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(root);
        files.stream().forEach(System.out::println);
        System.out.println("==================");

        //只要文件
        FluentIterable<File> filter = Files.fileTreeTraverser().preOrderTraversal(root).filter(f -> f.isFile());
        filter.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesPostOrderTraversal() {
        File root = new File("E:\\study-idea-workspace\\guava_programming\\src\\main");
        //倒序文件和目录
        FluentIterable<File> files = Files.fileTreeTraverser().postOrderTraversal(root);
        files.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesBreadthFirstTraversal() {
        File root = new File("E:\\study-idea-workspace\\guava_programming\\src\\main");
        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(root);
        files.stream().forEach(System.out::println);
    }

    @Test
    public void testTreeFilesChildren() {
        File root = new File("E:\\study-idea-workspace\\guava_programming\\src\\main");
        //root下的子目录
        Iterable<File> children = Files.fileTreeTraverser().children(root);
        children.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists())
            targetFile.delete();
    }


}
