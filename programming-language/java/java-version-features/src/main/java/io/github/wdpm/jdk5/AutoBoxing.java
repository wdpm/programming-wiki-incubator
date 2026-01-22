package io.github.wdpm.jdk5;

/**
 * 自动装箱：包装类型 = 基本类型
 * <br>
 * 自动拆箱：基本类型 = 包装类型
 *
 * @author evan
 * @since 2020/4/19
 */
public class AutoBoxing {
    public static void main(String[] args) {
        int     a = new Integer(123);
        Integer b = 123;

        double c = new Double("123.0");
        Double d = 123.0;

        long e = new Long("123L");
        Long f = 123L;

        char      g = new Character('a');
        Character h = 'a';

        float i = new Float("123.0f");
        Float j = 123.0f;

        boolean flag    = new Boolean(true);
        Boolean hasMore = true;

        short k = new Short("123");
        Short l = 123;

        byte m = new Byte("123");
        Byte n = 123;
    }
}
