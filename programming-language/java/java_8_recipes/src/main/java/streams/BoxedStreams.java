package streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoxedStreams {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listOfInt = IntStream.of(3, 1, 4, 1, 5, 9)
                .boxed()
                .collect(Collectors.toList());

        List<Integer> ints = IntStream.of(3, 1, 4, 1, 5, 9)
//                .collect(Collectors.toList());
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

//        IntStream.of(3, 1, 4, 1, 5, 9)
//                .collect(Collectors.toCollection(ArrayList<Integer>::new));

        int[] intArray = IntStream.of(3, 1, 4, 1, 5, 9)
                .toArray();
    }
}
