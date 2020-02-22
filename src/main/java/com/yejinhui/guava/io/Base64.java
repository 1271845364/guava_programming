package com.yejinhui.guava.io;

import com.google.common.base.Preconditions;
import sun.misc.BASE64Encoder;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Base64.*;

/**
 * @author ye.jinhui
 * @description
 * @program guava_programming
 * @create 2020/2/9 22:00
 */
public final class Base64 {

    private static final String CODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    private static final char[] CODE_DICT = CODE_STRING.toCharArray();

    private Base64() {
    }

    public static String encode(String plain) {
        checkNotNull(plain);
        StringBuilder result = new StringBuilder();
        String binaryString = toBinary(plain);
        //base64是6位位一组的,delat是需要补0的个数
        int delta = 6 - binaryString.length() % 6;
        for (int d = 0; d < delta && delta != 6; d++) {
            binaryString += "0";
        }
        for (int i = 0; i < binaryString.length() / 6; i++) {
            String binaryStr = binaryString.substring(i * 6, (i + 1) * 6);
            //二进制 --> 十进制
            int index = Integer.valueOf(binaryStr, 2);
            char c = CODE_DICT[index];
            result.append(c);
        }
        //二进制字符串后面补的0的个数，2个0位一个=
        if (delta != 6) {
            int total = delta / 2;
            for (int i = 0; i < total; i++) {
                result.append("=");
            }
        }
        return result.toString();
    }

    /**
     * 获取字符串的二进制
     *
     * @param source
     * @return
     */
    private static String toBinary(final String source) {
        StringBuilder binaryResult = new StringBuilder();
        for (int index = 0; index < source.length(); index++) {
            char c = source.charAt(index);
            String charBinary = Integer.toBinaryString(c);
            //二进制前面补0
            int delta = 8 - charBinary.length();
            for (int d = 0; d < delta; d++) {
                charBinary = "0" + charBinary;
            }
            binaryResult.append(charBinary);
        }
        return binaryResult.toString();
    }

    public static String decode(final String encrypt) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp = encrypt;
        //base64编码之后 =肯定是在字符串最后的
        int equalsIndex = temp.indexOf("=");
        if (equalsIndex > 0) {
            temp = temp.substring(0, equalsIndex);
        }
        //base64之后的字符串还原为二进制
        String toBinaryString = toBase64Binary(temp);
        for (int i = 0; i < toBinaryString.length() / 8; i++) {
            String str = toBinaryString.substring(i * 8, (i + 1) * 8);
            char c = Character.toChars(Integer.valueOf(str, 2))[0];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static String toBase64Binary(String source) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (char c : source.toCharArray()) {
            int charIndex = CODE_STRING.indexOf(c);
            String toBinaryString = Integer.toBinaryString(charIndex);
            int length = toBinaryString.length();
            if (length < 6) {
                for (int i = 0; i < 6 - length; i++) {
                    toBinaryString = "0" + toBinaryString;
                }
            }
            stringBuilder.append(toBinaryString);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("abc/+"));
        System.out.println(encode("sddf45d6g4er8"));

        System.out.println("===================");
        System.out.println(decode("YWJjLys="));
        System.out.println(decode("c2RkZjQ1ZDZnNGVyOA=="));
    }

}
