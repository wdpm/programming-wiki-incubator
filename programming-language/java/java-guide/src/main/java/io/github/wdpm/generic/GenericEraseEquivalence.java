package io.github.wdpm.generic;

import java.util.ArrayList;

/**
 * 泛型擦除的等价性
 *
 * @author evan
 * @date 2020/5/1
 */
public class GenericEraseEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }

    // true
}
