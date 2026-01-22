package io.github.wdpm.jdk14;

public class SwitchExpressionSample
{
    /**
     * Arrow labels
     *
     * @param k int
     * @link https://openjdk.java.net/jeps/361
     */
    static void howMany(int k)
    {
        switch (k) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            default -> System.out.println("many");
        }
    }

    /**
     * Switch expressions
     *
     * @param k int
     * @link https://openjdk.java.net/jeps/361
     */
    static void howManyV2(int k)
    {
        System.out.println(
                switch (k) {
                    case 1 -> "one";
                    case 2 -> "two";
                    default -> "many";
                }
        );
    }

    /**
     * yield a value
     *
     * @param s String
     * @link https://openjdk.java.net/jeps/361
     */
    static int yieldValue(String s)
    {
        return switch (s) {
            case "Foo":
                yield 1;
            case "Bar":
                yield 2;
            default:
                System.out.println("Neither Foo nor Bar, hmmm...");
                yield 0;
        };
    }


    public static void main(String[] args)
    {
        howMany(2);

        howManyV2(10);

        System.out.println(yieldValue("Bar"));
    }

}
