package com.yejinhui.guava.functional;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import javafx.beans.property.IntegerProperty;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Supplier类似工厂模式
 *
 * @author ye.jinhui
 * @description
 * @program guava
 * @create 2020/2/8 11:05
 */
public class FunctionExample {

    public static void main(String[] args) throws IOException {
        Function<String, Integer> function = new Function<String, Integer>() {

            @Override
            public Integer apply(String input) {
                Preconditions.checkNotNull(input, "The input Stream should not be null");
                return input.length();
            }
        };

        System.out.println(function.apply("Hello"));

        process("abc", new Handler.LengthDoubleHandler());

        System.out.println(Functions.toStringFunction().apply(new ServerSocket(8888)));

        //compose带有中间转换的
        Function<String, Double> compose = Functions.compose(new Function<Integer, Double>() {
            @Override
            public Double apply(Integer input) {
                return input * 1.0d;
            }
        }, new Function<String, Integer>() {
            @Override
            public Integer apply(String input) {
                return input.length();
            }
        });

        System.out.println(compose.apply("Hello"));
    }

    /**
     * 策略模式
     *
     * @param <IN>
     * @param <OUT>
     */
    interface Handler<IN, OUT> {
        OUT process(IN input);

        class LengthDoubleHandler implements Handler<String, Integer> {

            @Override
            public Integer process(String input) {
                return input.length() * 2;
            }
        }
    }

    private static void process(String text, Handler<String, Integer> handler) {
        System.out.println(handler.process(text));
    }
}
