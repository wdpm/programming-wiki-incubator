package collectors;

import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class ImmutableCollectionsTest {
    private ImmutableCollections demo = new ImmutableCollections();

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableList() {
        List<String> list = demo.createImmutableList("this", "is", "a", "list");
        assertEquals(4, list.size());
        assertEquals("this", list.get(0));
        assertEquals("is",   list.get(1));
        assertEquals("a",    list.get(2));
        assertEquals("list", list.get(3));
        list.add("extra");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableListJava7() {
        List<String> list = demo.createImmutableListJava7("this", "is", "a", "list");
        assertEquals(4, list.size());
        assertEquals("this", list.get(0));
        assertEquals("is",   list.get(1));
        assertEquals("a",    list.get(2));
        assertEquals("list", list.get(3));
        list.add("extra");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableSet() {
        Set<String> set = demo.createImmutableSet("a", "b", "b", "c");
        assertEquals(3, set.size());
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        set.add("d");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void createImmutableSetJava7() {
        Set<String> set = demo.createImmutableSetJava7("a", "b", "b", "c");
        assertEquals(3, set.size());
        assertTrue(set.contains("a"));
        assertTrue(set.contains("b"));
        assertTrue(set.contains("c"));
        set.add("d");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableMap() {
        demo.map.put("a", 5);
    }

}