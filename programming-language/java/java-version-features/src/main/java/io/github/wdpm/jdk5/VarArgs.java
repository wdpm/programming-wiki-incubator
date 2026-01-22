package io.github.wdpm.jdk5;

import java.util.List;

/**
 * 可变参数
 * @author evan
 * @since 2020/4/19
 */
public class VarArgs {
    public static void main(String[] args) {
        printArgs(1, "2", "3");
    }

    // 一个一个按顺序传参
    public static void printArgs(String arg1, String arg2, String arg3) {
    }

    // 将全部参数封装于一个数组
    public static void printArgs(List<String> args) {
    }

    // 先是常规参数，然后是可变参数（必须为最后一项）
    public static void printArgs(int arg1, String... otherArgs) {
        System.out.println("arg1: " + arg1);
        System.out.print("otherArgs: ");
        for (String arg : otherArgs) {
            System.out.print(arg + " ");
        }
    }
}
