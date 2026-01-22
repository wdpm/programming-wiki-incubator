package io.github.wdpm.jdk7;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型类型推断
 *
 * @author evan
 * @since 2020/4/19
 */
public class GenericTypeInference {
    public static void main(String[] args) {
        List<Comparable> list = new ArrayList<>();
        list.add("hello");

        // Integer extends Number implements Comparable<Integer>
        List<Integer> list2 = new ArrayList<>();
        list2.add(123);

        // addAll(Collection<? extends E> c)
        list.addAll(list2);

        System.out.println(list);
    }
}
