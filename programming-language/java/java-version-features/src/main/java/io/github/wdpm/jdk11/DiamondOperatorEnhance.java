package io.github.wdpm.jdk11;

/**
 * 菱形操作符<>的加强
 *
 * @author evan
 * @date 2020/5/2
 */
public class DiamondOperatorEnhance {
    abstract static class MyHandler<T> {
        private T value;

        public MyHandler(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        abstract void handle();
    }

    public static void main(String[] args) {
        MyHandler<?> handler = new MyHandler<>("oh-no") {
            @Override
            void handle() {
                System.out.println(getValue());
            }
        };

        handler.handle();
    }
}
