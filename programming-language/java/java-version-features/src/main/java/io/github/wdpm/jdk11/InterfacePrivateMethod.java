package io.github.wdpm.jdk11;

/**
 * 接口支持私有方法
 *
 * @author evan
 * @date 2020/5/2
 */
public interface InterfacePrivateMethod {

    void say();

    /**
     * call foo()
     */
    default void bar() {
        foo();
    }

    /**
     * call foo()
     */
    default void baz() {
        foo();
    }

    private void foo() {
        System.out.println("private foo()");
    }
}
