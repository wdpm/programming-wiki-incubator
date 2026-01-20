package streams;

import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class SumStringLengths {
    public static void main(String[] args) {
        String[] strings = "this is an array of strings".split(" ");
        long count = Arrays.stream(strings)
                .map(String::length)
                .count();

        System.out.println("There are " + count + " strings");

        int totalLength = Arrays.stream(strings)
                .mapToInt(String::length)
                .sum();

        System.out.println("The total length is " + totalLength);

        OptionalDouble ave = Arrays.stream(strings)
                .mapToInt(String::length)
                .average();

        System.out.println("The average length is " + ave);

        OptionalInt max = Arrays.stream(strings)
                .mapToInt(String::length)
                .max();

        OptionalInt min = Arrays.stream(strings)
                .mapToInt(String::length)
                .min();

        System.out.println("The max and min lengths are " + max + " and " + min);

    }
}
