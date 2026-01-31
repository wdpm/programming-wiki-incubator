package io.github.wdpm.idea.tips;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Editor ->  Code Style -> Java -> Wrapping and Braces:
 * <pre>
 * - Chained method calls [Wrap always]
 *   - Wrap first call [ ]
 *   - Align when multiline [√]
 * </pre>
 *
 * @Created evan
 * @Date 2020/4/19
 */
public class StreamStyle {
    public List<Integer> evenNumbers(List<Integer> integers) {
        return integers.stream()
                       .filter(i -> i % 2 == 0)
                       .collect(Collectors.toList());
    }
}
