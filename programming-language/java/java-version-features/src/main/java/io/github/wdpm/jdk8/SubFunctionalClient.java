package io.github.wdpm.jdk8;

/**
 * 测试函数式接口的默认方法和静态方法
 *
 * @author evan
 * @since 2020/4/19
 */
public class SubFunctionalClient implements FunctionalMethod {

    @Override
    public void method1() {
        System.out.println("SubFunctional method1");
    }

    public static void main(String[] args) {
        SubFunctionalClient subFunctionalClient = new SubFunctionalClient();
        subFunctionalClient.method1();
        subFunctionalClient.method2();
        FunctionalMethod.method3();

        /*
        SubFunctional method1
        FunctionalMethod default method2
        FunctionalMethod static method3
         */
    }
}
