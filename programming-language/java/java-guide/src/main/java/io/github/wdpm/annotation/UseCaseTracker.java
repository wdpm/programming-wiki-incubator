package io.github.wdpm.annotation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * UseCaseClient类的注解处理
 *
 * @author evan
 * @date 2020/5/2
 */
public class UseCaseTracker {
    public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
        for (Method m : cl.getDeclaredMethods()) {
            UseCase uc = m.getAnnotation(UseCase.class);
            if (uc != null) {
                System.out.println("Found Use Case " + uc.id() + "\n"
                        + uc.description());
                useCases.remove(Integer.valueOf(uc.id()));
            }
        }
        useCases.forEach(i -> System.out.println("Missing use case " + i));
    }

    public static void main(String[] args) {
        List<Integer> useCases = IntStream.range(1, 3)
                                          .boxed().collect(Collectors.toList());
        trackUseCases(useCases, UseCaseClient.class);
    }

    // Found Use Case 1
    // Passwords must contain at least one numeric
    // Missing use case 2
}
