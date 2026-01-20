package generics;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class Demo {
    private static void printList(List<?> list) {
        System.out.println(list);
    }

    private static double sumList(List<? extends Number> list) {
        return list.stream()
                .mapToDouble(Number::doubleValue)
                .sum();
    }

    private static void numsUpTo(Integer num, List<? super Integer> output) {
        IntStream.rangeClosed(1, num)
                .forEach(output::add);
    }

    public static List<Employee> createEmployees() {
        return Arrays.asList(
                new Employee(1, "Seth Curry"),
                new Employee(2, "Kevin Durant"),
                new Employee(3, "Draymond Green"),
                new Employee(4, "Klay Thompson")
        );
    }

    public static void main(String[] args) {
        List<String> strings = new ArrayList<>();
        strings.add("Hello");
        strings.add("World");

        for (String s : strings) {
            System.out.printf("%s has length %d%n", s, s.length());
        }

        List<Integer> ints = new ArrayList<>();
        ints.add(3);
        ints.add(1);
        ints.add(4);
        ints.add(1);
        ints.add(9);
        ints.add(2);
        System.out.println(ints);

        for (int i : ints) {
            System.out.println(i);
        }

        List<Object> objects = new ArrayList<>();
        objects.add("Hello");
        objects.add(LocalDate.now());
        objects.add(3);
        System.out.println(objects);

        String s = "abc";
        Object o = s;      // allowed
        // strings.add(o); // not allowed

        // List<Object> moreObjects = strings; // also not allowed, but pretend it was
        // moreObjects.add(new Date());
        // String s = moreObjects.get(0); // uh, oh

        List<?> stuff = new ArrayList<>();
        // stuff.add("abc");
        // stuff.add(new Object());
        // stuff.add(3);
        int numElements = stuff.size();
        System.out.println("numElements = " + numElements);

        printList(ints);
        printList(strings);
        printList(stuff);

        List<? extends Number> numbers = new ArrayList<>();
//        numbers.add(3);
//        numbers.add(3.14159);
//        numbers.add(new BigDecimal("3"));


        ints = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<BigDecimal> bigDecimals = Arrays.asList(
                new BigDecimal("1.0"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("4.0"),
                new BigDecimal("5.0")
        );


        System.out.printf("The sum of the ints is         %s%n", sumList(ints));
        System.out.printf("The sum of the doubles is      %s%n", sumList(doubles));
        System.out.printf("The sum of the big decimals is %s%n", sumList(bigDecimals));

        ArrayList<Integer> integerList = new ArrayList<>();
        ArrayList<Number> numberList = new ArrayList<>();
        ArrayList<Object> objectList = new ArrayList<>();

        numsUpTo(5, integerList);
        numsUpTo(5, numberList);
        numsUpTo(5, objectList);

        System.out.println(integerList);
        System.out.println(numberList);
        System.out.println(objectList);

        List<Employee> employees = createEmployees();
        Employee maxId = employees.stream()
                .max(new Comparator<Employee>() {
                    @Override
                    public int compare(Employee e1, Employee e2) {
                        return e1.getId() - e2.getId();
                    }
                }).orElse(Employee.DEFAULT_EMPLOYEE);
        Employee maxName = employees.stream()
                .max(new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        return o1.toString().compareTo(o2.toString());
                    }
                }).orElse(Employee.DEFAULT_EMPLOYEE);
        System.out.println("Max id is " + maxId);
        System.out.println("Max name is " + maxName);

        maxId = employees.stream()
                .max(comparingInt(Employee::getId)).orElse(Employee.DEFAULT_EMPLOYEE);
        maxName = employees.stream()
                .max(comparing(Object::toString)).orElse(Employee.DEFAULT_EMPLOYEE);
        System.out.println(maxId);
        System.out.println(maxName);

        List<String> names = employees.stream()
                .map(Employee::getName)
                .collect(toList());

        // Add employees to a map using id as key
        Map<Integer, Employee> employeeMap = employees.stream()
                .collect(toMap(Employee::getId, Function.identity()));

        // Sort employees by id and print them
        System.out.println("Sorted by key:");
        employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });

        // Sort employees by name and print them
        System.out.println("Sorted by name:");
        employeeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(comparing(Employee::getName)))
                // .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });
    }
}
