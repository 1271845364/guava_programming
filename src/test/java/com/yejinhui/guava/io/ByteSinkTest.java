package com.yejinhui.guava.io;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSink;
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
 * @create 2020/2/9 17:41
 */
public class ByteSinkTest {

    private static final String path = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\ByteSinkTest.dat";

    @Test
    public void testByteSink() throws IOException {
        File file = new File(path);
        file.deleteOnExit();
        ByteSink byteSink = Files.asByteSink(file);
        byte[] bytes = {0x01, 0x02};
        byteSink.write(bytes);

        byte[] result = Files.toByteArray(file);
        assertThat(result, equalTo(bytes));
    }

    @Test
    public void testFromByteSourceToByteSink() throws IOException {
        String source = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\Git.jpg";
        String target = "E:\\study-idea-workspace\\guava_programming\\src\\test\\resources\\io\\Git-2.jpg";
        File sourceFile = new File(source);
        File sinkFile = new File(target);

        sinkFile.deleteOnExit();
        ByteSource byteSource = Files.asByteSource(sourceFile);
        byteSource.copyTo(Files.asByteSink(sinkFile));
        assertThat(sinkFile.exists(),equalTo(true));

        HashCode sourceHashCode = Files.hash(sourceFile, Hashing.sha256());
        HashCode sinkHashCode = Files.hash(sinkFile, Hashing.sha256());
        assertThat(sinkHashCode, equalTo(sourceHashCode));
    }

}
