package tasks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class UseTasks {
    public static void main(String[] args) {
        List<Task> tasks = Arrays.asList(
                new Task(1, true, 3, "Defeat the Black Knight"),
                new Task(2, true, 5, "Answer the Bridgekeeper"),
                new Task(3, false, 6, "Run away from killer rabbit"),
                new Task(4, true, 4, "Lobbeth thou thy Holy Hand Grenade"),
                new Task(5, false, 5, "Give a shrubbery to the Knights Who Say Ni!"),
                new Task(6, true, 1, "Get taunted by Frenchman"),
                new Task(7, true, 2, "Weigh a witch against a duck"),
                new Task(8, false, 4, "Dance and sing and imitate Clark Gable"),
                new Task(9, true, 3, "Build a giant wooden hare"),
                new Task(10, false, 2, "Attack Swamp Castle"),
                new Task(11, true, 1, "Applaud Tim the Enchanter"),
                new Task(12, false, 2, "Oppress peasant")
                // new Task(null, true, 2, "Ask hard questions during Java 8 talk")
        );


        double sumIdOfActiveTasks = tasks.stream()
                .filter((task) -> task.isActive() && task.getId() != null)
                .mapToInt(Task::getId)
                .sum();
        System.out.println("Sum of id's of active tasks: " + sumIdOfActiveTasks);

        double averageDuration = tasks.stream()
                .filter(Task::isActive)
                .mapToInt(Task::getDuration)
                .average().orElse(0.0);

        System.out.printf(
                "The average duration of the active tasks is %s%n",
                averageDuration);

        // Sort by duration
        List<Task> taskList = tasks.stream()
                .sorted(comparing(Task::getDuration))
                .collect(toList());

        System.out.println("Sorted by duration:");
        taskList.forEach(System.out::println);

        // Sort by duration, then by name alphabetically
        taskList = tasks.stream()
                .sorted(comparing(Task::getDuration)
                        .thenComparing(Task::getName))
                .collect(toList());

        System.out.println("Sorted by duration then by name");
        taskList.forEach(System.out::println);

        // Sort by duration, then by name, reverse alpha
        taskList = tasks.stream()
                .sorted(comparing(Task::getDuration)
                        .thenComparing(Task::getName).reversed())
                .collect(toList());

        // Group tasks by duration
        Map<Integer, List<Task>> taskMap = tasks.stream()
                .collect(groupingBy(Task::getDuration));
        taskMap.forEach((key, val) ->
                System.out.printf("%s = %s%n", key, val));


        // Collect the tasks into a set
        Set<Task> taskSet = tasks.stream()
                .collect(Collectors.toSet());
        System.out.println(taskList);


        // Partition tasks in active and inactive
        Map<Boolean, List<Task>> taskPartition = tasks.stream()
                .collect(partitioningBy(Task::isActive));

        System.out.println("Tasks grouped by isActive:");
        taskPartition.forEach((key, val) -> {
            System.out.println(key);
            val.forEach(System.out::println);
        });

        // Group by durations
        Map<Integer, List<Task>> taskDurations = tasks.stream()
                .collect(groupingBy(Task::getDuration));
        taskDurations.forEach((k, v) -> System.out.println(k + " : " + v));

        // Group task names by duration (downstream collector)
        Map<Integer, List<String>> nameMap = tasks.stream()
                .collect(groupingBy(Task::getDuration,
                        mapping(Task::getName, toList())));
        nameMap.forEach((k, v) -> System.out.println(k + " : " + v));

        // Group task names by duration (downstream collector)
        Map<Integer, Long> numInEachGroup = tasks.stream()
                .collect(groupingBy(Task::getDuration, counting()));
        nameMap.forEach((k, v) -> System.out.println(k + " : " + v));

    }
}

