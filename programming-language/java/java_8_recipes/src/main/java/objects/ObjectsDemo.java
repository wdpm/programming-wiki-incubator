package objects;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectsDemo {
    private List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }

    public List<String> getNonNullStrings() {
        return strings.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public <T> List<T> getNonNullElements(List<T> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
