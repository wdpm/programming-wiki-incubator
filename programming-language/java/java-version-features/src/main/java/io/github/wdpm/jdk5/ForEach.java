package io.github.wdpm.jdk5;

/**
 * for-each循坏
 *
 * @author evan
 * @since 2020/4/19
 */
public class ForEach {
    public static void main(String[] args) {
        int[] ints = {1, 2, 3, 4};
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
}
