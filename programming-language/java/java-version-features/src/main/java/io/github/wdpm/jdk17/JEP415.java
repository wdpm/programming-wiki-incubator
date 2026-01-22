package io.github.wdpm.jdk17;

import java.io.*;


public class JEP415 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dog dog = new Dog("泰迪");
        dog.setPoc(new Poc());

        // 序列化 - 对象转字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
            objectOutputStream.writeObject(dog);
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();
        // 反序列化 - 字节数组转对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        // 允许自定义类，允许 java.base 中的所有类，拒绝其他任何类
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
                "io.github.wdpm.jdk17.*;java.base/*;!*");
        objectInputStream.setObjectInputFilter(filter);

        Object object = objectInputStream.readObject();
        System.out.println(object.toString());
    }
}

class Dog implements Serializable {
    private String name;
    private Poc poc;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" + "name='" + name + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Poc getPoc() {
        return poc;
    }

    public void setPoc(Poc poc) {
        this.poc = poc;
    }
}

class Poc implements Serializable {

}