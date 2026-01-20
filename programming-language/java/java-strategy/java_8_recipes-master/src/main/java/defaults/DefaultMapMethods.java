package defaults;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.LongStream;

public class DefaultMapMethods {
    private Map<Long, BigInteger> cache = new ConcurrentHashMap<>();
    private Map<Long, Long> longCache = new HashMap<>();

    public BigInteger fib(long i) {
        if (i == 0) return BigInteger.ZERO;
        if (i == 1) return BigInteger.ONE;

        return cache.computeIfAbsent(i, n -> fib(n - 2).add(fib(n - 1)));
    }

    // Overflows a long at longfib(93)
    public long longfib(long i) {
        if (i == 0) return 0;
        if (i == 1) return 1;

        return longCache.computeIfAbsent(i, n -> longfib(n - 2) + longfib(n - 1));
    }

    public Map<String,Integer> countWords(String passage, String... strings) {
        Map<String, Integer> wordCounts = new HashMap<>();

        // Put the words we care about in the map with a count of zero
        Arrays.stream(strings).forEach(s -> wordCounts.put(s, 0));

        // Read the passage, updating the counts only for the words we care about
        Arrays.stream(passage.split(" ")).forEach(word ->
            wordCounts.computeIfPresent(word, (key, val) -> val + 1));

        return wordCounts;
    }

    public Map<String, Integer> fullWordCounts(String passage) {
        Map<String, Integer> wordCounts = new HashMap<>();
        String testString = passage.toLowerCase().replaceAll("\\W"," ");

        Arrays.stream(testString.split("\\s+")).forEach(word ->
            wordCounts.merge(word, 1, Integer::sum));

        return wordCounts;
    }

    public static void main(String[] args) {
        DefaultMapMethods demo = new DefaultMapMethods();
        LongStream.range(1, 100)
                .forEach(n -> System.out.printf("%3d: %21d %21d%n", n, demo.fib(n), demo.longfib(n)));

        String passage = "NSA agent walks into a bar. Bartender says, " +
                "'Hey, I have a new joke for you.' NSA agent says, 'heard it'.";
        Map<String, Integer> counts = demo.countWords(passage, "NSA", "agent", "joke");
        counts.forEach((word, count) -> System.out.println(word + "=" + count));

        Map<String, Integer> map = demo.fullWordCounts(passage);
        System.out.println(map);
        System.out.println(map.getOrDefault("nope", 0));
    }
}
