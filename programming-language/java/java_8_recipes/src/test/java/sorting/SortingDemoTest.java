package sorting;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class SortingDemoTest {
    private final SortingDemo demo = new SortingDemo();

    private void checkAlphabetical(List<String> strings) {
        strings.stream()
                .reduce((prev, curr) -> {
                            assertTrue(prev.compareTo(curr) <= 0);
                            return curr;
                        }
                );
    }

    private void checkLength(List<String> strings) {
        strings.stream()
                .reduce((prev, curr) -> {
                            assertTrue(prev.length() <= curr.length());
                            return curr;
                        }
                );
    }

    @Test
    public void testAlphaSort() {
        checkAlphabetical(demo.alphaSort());
    }

    @Test
    public void testLengthReverseSortWithComparator() {
        List<String> strings = demo.lengthReverseSortWithComparator().stream()
                // -1 表示反转整个列表
                .sorted((s1, s2) -> -1)
                .collect(Collectors.toList());
        // System.out.println(strings);
        checkLength(strings);
    }

    @Test
    public void testLengthSortWithLambda() {
        checkLength(demo.lengthSortWithLambda());
    }

    // 下面这个三个测试方法似乎没实现

    @Test
    public void testLengthSortUsingCompare() {
        checkLength(demo.lengthSortUsingComparator());
    }

    @Test
    public void testLengthSortVerbose() {
        checkLength(demo.lengthSortWithLambda());
    }

    @Test
    public void testLengthSortComparator() {
        checkLength(demo.lengthSortUsingComparator());
    }

    @Test
    public void testLengthSortThenAlphaSort() {
        demo.lengthSortThenAlphaSort().stream()
                .reduce((prev, curr) -> {
                    if (prev.length() != curr.length()) {
                        assertTrue(prev.length() <= curr.length());
                    } else {
                        assertTrue(prev.compareTo(curr) <= 0);
                    }
                    return curr;
                });
    }

    @Test
    public void testLengthSortUsingSorted() {
        checkLength(demo.lengthSortUsingSorted());
    }

    @Test
    public void testLengthSortThenAlphaSortUsingSorted() {
        demo.lengthSortThenAlphaSortUsingSorted().stream()
                .reduce((prev, curr) -> {
                    if (prev.length() != curr.length()) {
                        assertTrue(prev.length() <= curr.length());
                    } else {
                        assertTrue(prev.compareTo(curr) <= 0);
                    }
                    return curr;
                });
    }
}