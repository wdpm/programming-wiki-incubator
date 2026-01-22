package io.github.wdpm.jdk8;

/**
 * 函数式接口的默认方法和静态方法
 *
 * @author evan
 * @since 2020/4/19
 */
@FunctionalInterface
public interface FunctionalMethod {

    void method1();

    // 默认方法。子类可以不实现默认方法
    default void method2() {
        System.out.println("FunctionalMethod default method2");
    }

    // 静态方法
    static void method3() {
        System.out.println("FunctionalMethod static method3");
    }
}
