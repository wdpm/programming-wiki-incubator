package io.github.wdpm.jdk18.javadoc;

/**
 * 在 Java 18 之后可以使用新的方式
 * 下面的代码演示如何使用 {@code Optional.isPresent}:
 * {@snippet :
 * if (v.isPresent()) {
 *     System.out.println("v:" + v.get());
 * }
 *}
 * <p>
 * 高亮显示 println
 * <p>
 * {@snippet :
 * class HelloWorld {
 *     public static void main(String... args) {
 *         System.out.println("Hello World!");      // @highlight substring="println"
 *     }
 * }
 *}
 */
public class SnippetHighlightExample {
}


