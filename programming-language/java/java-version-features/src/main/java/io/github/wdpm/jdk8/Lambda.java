package io.github.wdpm.jdk8;

import java.util.Arrays;

/**
 * Lambda
 *
 * @author evan
 * @since 2020/4/19
 */
public class Lambda {
    public static void main(String[] args) {
        Arrays.asList("a", "b", "c").forEach(System.out::println);
    }
}
