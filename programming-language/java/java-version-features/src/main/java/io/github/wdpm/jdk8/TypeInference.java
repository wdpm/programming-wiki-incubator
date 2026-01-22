package io.github.wdpm.jdk8;

/**
 * 增强泛型的类型推断
 *
 * @author evan
 * @since 2020/4/19
 */
public class TypeInference<T> {
    /**
     * get default value.
     *
     * @param <T>
     * @return null
     */
    public static <T> T defaultValue() {
        return null;
    }

    public T getOrDefault(T value, T defaultValue) {
        return (value != null) ? value : defaultValue;
    }

    public static void main(String[] args) {
        final TypeInference<String> typeInference = new TypeInference<>();
        typeInference.getOrDefault("123", TypeInference.defaultValue());
    }

}
