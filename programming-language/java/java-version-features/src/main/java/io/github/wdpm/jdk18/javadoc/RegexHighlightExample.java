package io.github.wdpm.jdk18.javadoc;

/**
 * 正则高亮：
 * {@snippet :
 *   public static void main(String... args) {
 *       for (var arg : args) {                 // @highlight region regex = "\barg\b"
 *           if (!arg.isBlank()) {
 *               System.out.println(arg);
 *           }
 *       }                                      // @end
 *   }
 *}
 */
public class RegexHighlightExample {

}