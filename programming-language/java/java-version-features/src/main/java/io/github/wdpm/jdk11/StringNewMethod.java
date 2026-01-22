package io.github.wdpm.jdk11;

import java.util.stream.Stream;

/**
 * String新方法
 *
 * @author evan
 * @date 2020/5/2
 */
public class StringNewMethod {
    public static void main(String[] args) {
        // lines();
        // isBlank();
        // stripLeading();
        // stripTrailing();
        // strip();
        // repeat();
    }

    /**
     * str lines() 行流
     */
    public static void lines() {
        String         str   = "hello \nworld \ndone.";
        Stream<String> lines = str.lines();
        lines.forEach(System.out::println);
    }

    /**
     * str isBlank() 更好地检查空意义的字符
     */
    public static void isBlank() {
        String emptyString = "";
        System.out.println("empty string -> " + emptyString.isBlank());

        String onlyLineSeparator = System.getProperty("line.separator");
        System.out.println("line.separator -> " + onlyLineSeparator.isBlank());

        String tabOnly = "\t";
        System.out.println("Tab -> " + tabOnly.isBlank());

        String spacesOnly = "   ";
        System.out.println("space -> " + spacesOnly.isBlank());
    }

    /**
     * str stripLeading() 去除字符串开头的空字符
     */
    public static void stripLeading() {
        String str = "  wdpm  123  ";
        System.out.println("'" + str.stripLeading() + "'");
    }

    /**
     * str stripTrailing() 去除字符串末尾的空字符
     */
    public static void stripTrailing() {
        String str = "  wdpm  123  ";
        System.out.println("'" + str.stripTrailing() + "'");
    }

    /**
     * str strip() 去除首尾的空字符
     */
    public static void strip() {
        String str = "  wdpm  123  ";
        System.out.println("'" + str.strip() + "'");
    }

    /**
     * str repeat() 字符串自定义重复次数
     */
    public static void repeat() {
        System.out.println("123".repeat(4));
    }
}
