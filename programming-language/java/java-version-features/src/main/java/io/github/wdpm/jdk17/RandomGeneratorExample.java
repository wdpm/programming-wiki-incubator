package io.github.wdpm.jdk17;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

public class RandomGeneratorExample {
    public static void main(String[] args) {
        // 使用ThreadLocalRandom生成随机数生成器
        RandomGenerator randomGenerator = ThreadLocalRandom.current();

        // 获取0~9之间的随机整数
        int randomInt = randomGenerator.nextInt(10);
        System.out.println("随机整数：" + randomInt);

        // 获取0.0~1.0之间的随机浮点数
        double randomDouble = randomGenerator.nextDouble();
        System.out.println("随机浮点数：" + randomDouble);

        // 使用自定义随机数生成器实现
        IntSupplier customGenerator = () -> randomGenerator.nextInt(100);
        IntStream customRandomIntStream = IntStream.generate(customGenerator);

        // 获取10个0~99之间的随机整数
        customRandomIntStream.limit(10).forEach(num -> System.out.println("随机整数：" + num));
    }
}
