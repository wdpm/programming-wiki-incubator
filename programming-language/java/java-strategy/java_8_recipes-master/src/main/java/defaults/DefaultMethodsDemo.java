package defaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultMethodsDemo {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(-3); nums.add(1); nums.add(4);
        nums.add(-1); nums.add(5); nums.add(9);
        System.out.println(nums);

        // removeIf is a default method in Collection
        // returns true if any elements were removed
        boolean removed = nums.removeIf(n -> n <= 0);
        System.out.println("Elements were" + (removed ? " " : "NOT ") + "removed");
        System.out.println(nums);

        // Iterator has a default forEach method
        nums.forEach(System.out::println);
    }
}
