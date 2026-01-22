package io.github.wdpm.jdk9;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImmutableCollectionSample
{
    // in Java 8
    Map<Integer, String> emptyMap          = new HashMap<>();
    Map<Integer, String> immutableEmptyMap = Collections.unmodifiableMap(emptyMap);

    // now in Java 9
    List immutableList  = List.of();
    List immutableList2 = List.of("one", "two", "three");

    Map emptyImmutableMap    = Map.of();
    Map nonemptyImmutableMap = Map.of(1, "one", 2, "two", 3, "three");
}
