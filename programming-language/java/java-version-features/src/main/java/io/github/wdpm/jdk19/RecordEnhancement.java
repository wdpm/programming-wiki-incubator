package io.github.wdpm.jdk19;

public class RecordEnhancement {
    public static void main(String[] args) {
        Object dog1 = new Dog("Dog", 1);
        if (dog1 instanceof Dog(String name, Integer age)) {
            System.out.println(name + ":" + age);
        }
    }

    record Dog(String name, Integer age) {
    }
}