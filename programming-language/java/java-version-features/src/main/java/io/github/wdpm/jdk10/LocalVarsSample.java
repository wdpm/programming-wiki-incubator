package io.github.wdpm.jdk10;

import java.util.List;

public class LocalVarsSample
{
    public static void main(String[] args)
    {
        var numbers = List.of(1, 2, 3, 4, 5); // inferred Array
        // Index of Enhanced For Loop
        for (var number : numbers) {
            System.out.println(number);
        }
        // Local variable declared in a loop
        for (var i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));
        }

        // check value type
        var name = "foo";
        System.out.println(name);
        System.out.println(name instanceof String);
    }
}
