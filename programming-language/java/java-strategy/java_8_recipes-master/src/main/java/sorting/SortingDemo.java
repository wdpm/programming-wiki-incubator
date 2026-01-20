package sorting;

import java.util.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

@SuppressWarnings({"Convert2Lambda", "ComparatorCombinators", "unused", "Java8ListSort"})
public class SortingDemo {
    private List<String> sampleStrings =
            Arrays.asList("this", "is", "a", "list", "of", "strings");

    // Default sort from Java 7-
    public List<String> alphaSort() {
        Collections.sort(sampleStrings);
        return sampleStrings;
    }

    // Default sort from Java 8+
    public List<String> alphaSortUsingStreams() {
        return sampleStrings.stream()
                .sorted()
                .collect(toList());
    }

    // Java 7- using Comparator with anonymous inner class
    public List<String> lengthReverseSortWithComparator() {
        Collections.sort(sampleStrings, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.length() - s1.length();
            }
        });
        return sampleStrings;
    }

    // Using a lambda as a Comparator with a lambda
    public List<String> lengthSortWithLambda() {
        Collections.sort(sampleStrings,
                (s1, s2) -> s1.length() - s2.length());
        return sampleStrings;
    }

    // Sort by length with sorted
    public List<String> lengthSortUsingSorted() {
        return sampleStrings.stream()
                .sorted((s1, s2) -> s1.length() - s2.length())
                .collect(toList());
    }

    // Length sort with comparingInt
    public List<String> lengthSortUsingComparator() {
        return sampleStrings.stream()
                .sorted(comparingInt(String::length))
                .collect(toList());
    }

    // Length sort, then alphabetical
    public List<String> lengthSortThenAlphaSort() {
        Collections.sort(sampleStrings,
                comparingInt(String::length)
                        .thenComparing(naturalOrder()));
        return sampleStrings;
    }

    // Sort by length then alpha using sorted
    public List<String> lengthSortThenAlphaSortUsingSorted() {
        return sampleStrings.stream()
                .filter(Objects::nonNull)
                .sorted(comparingInt(String::length)
                        .thenComparing(naturalOrder()))
                .collect(toList());
    }

    // Sort by length then reverse alpha using sorted
    public List<String> lengthSortThenReverseAlphaUsingSorted() {
        return sampleStrings.stream()
                .filter(Objects::nonNull)
                .sorted(comparing(String::length)
                        .thenComparing(reverseOrder()))
                .collect(toList());
    }
}
