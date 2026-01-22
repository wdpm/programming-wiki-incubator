package io.github.wdpm.jdk5;

/**
 * 泛型
 *
 * @author evan
 * @since 2020/4/19
 */
public class Generic<T>
{
    private T t;

    public Generic(T t)
    {
        this.t = t;
    }

    public T getT()
    {
        return t;
    }

    public void setT(T t)
    {
        this.t = t;
    }

    public static void main(String[] args)
    {
        Generic<Integer> generic = new Generic<>(123);
        Integer          t       = generic.getT();
        generic.setT(10);
    }
}
