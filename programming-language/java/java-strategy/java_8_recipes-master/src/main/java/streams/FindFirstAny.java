package streams;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FindFirstAny {
    private static Integer delay(Integer n) {
        try {
            System.out.println(Thread.currentThread().getName());
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException ignored) {
        }
        return n;
    }

    public static void main(String[] args) {
        // First even number
        Optional<Integer> firstEven = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .filter(n -> n % 2 == 0)
                .findFirst();

        System.out.println(firstEven);

        // findFirst on an empty stream
        Optional<Integer> firstEvenGT10 = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .filter(n -> n > 10)
                .filter(n -> n % 2 == 0)
                .findFirst();

        System.out.println(firstEvenGT10);

        // findFirst in parallel (no difference because stream has an encounter order)
        firstEven = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .parallel()
                .filter(n -> n % 2 == 0)
                .findFirst();

        System.out.println(firstEven);


        Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .unordered()
                .forEach(System.out::println);

        Optional<Integer> any = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .unordered()
                .map(FindFirstAny::delay)
                .findAny();

        System.out.println("Sequential Any: " + any);

        any = Stream.of(3, 1, 4, 1, 5, 9, 2, 6, 5)
                .unordered()
                .parallel()
                .map(FindFirstAny::delay)
                .findAny();

        System.out.println("Parallel Any: " + any);

        Set<String> words = new HashSet<>();
        words.addAll(Arrays.asList("this", "is", "a", "stream", "of", "strings"));
        Optional<String> firstString = words.stream()
                .parallel() // doesn't change anything in Java 8
                .findFirst();
        System.out.println("Set parallel: " + firstString);

        List<String> wordList = Arrays.asList("this", "is", "a", "stream", "of", "strings");
        words = new HashSet<>();
        words.addAll(wordList);
        firstString = words.stream()
                .findFirst();
        System.out.println(firstString);

        firstString = Stream.of("this", "is", "a", "stream", "of", "strings")
                .unordered()
                .findFirst();
        System.out.println(firstString);

        // Recommend based on Stuart Marks anser to S.O. question
        Set<String> words2 = new HashSet<>(words);

        // Now add and remove enough elements to force a rehash
        IntStream.rangeClosed(0, 50).forEachOrdered(i -> words2.add(String.valueOf(i)));
        words2.retainAll(wordList);

        System.out.println(words.equals(words2));
        System.out.println("Before: " + words);
        System.out.println("After : " + words2);

        // Now findFirst will return a different word, because the set has been rehashed
        //
        // Note in Java 9, "the new immutable sets (and maps) are randomized, so their
        // iteration orders will change from run to run, even if they are initialized
        // the same way every time."
    }
}
