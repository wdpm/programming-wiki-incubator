package collectors;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectorsDemoTest {
    private CollectorsDemo demo = new CollectorsDemo();

    @Test
    public void createList() {
        List<String> nameList = demo.createList();
        assertEquals(7, nameList.size());
        assertEquals(ArrayList.class, nameList.getClass());
    }

    @Test
    public void createSet() {
        Set<String> nameSet = demo.createSet();
        assertEquals(6, nameSet.size());
        assertEquals(HashSet.class, nameSet.getClass());
    }

    @Test
    public void createDeque() {
        Deque<String> nameDeque = demo.createDeque();
        assertEquals(7, nameDeque.size());
        assertEquals(ArrayDeque.class, nameDeque.getClass());
    }

    @Test
    public void createArray() {
        String[] names = demo.createArray();
        assertEquals(4, names.length);
    }

    @Test
    public void evenLengthStrings() {
        List<String> evenLengthStrings =
                demo.evenLengthStrings("here", "are", "a", "few", "strings");
        evenLengthStrings.forEach(s -> {
            assertTrue(s.length() % 2 == 0);
        });
    }

    @Test
    public void evenLengthStringSet() {
        SortedSet<String> stringSet = demo.oddLengthStringSet("here", "are", "a", "few", "strings");
        System.out.println(stringSet);
        stringSet.forEach(s -> assertTrue(s.length() % 2 != 0));
        stringSet.first().equals("a");
        stringSet.last().equals("strings");
    }
}