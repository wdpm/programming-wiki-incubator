package io.github.wdpm.jdk8;

import java.util.Optional;

/**
 * Optional基本使用，消除NPE
 *
 * @author evan
 * @since 2020/4/19
 */
public class OptionalClient {
    public static void main(String[] args) {
        Optional<String> lastName = Optional.ofNullable(null);
        System.out.println("Is the lastName set? " + lastName.isPresent());
        System.out.println("LastName is: " + lastName.orElse("[None]"));
        System.out.println("Upper lastName: " + lastName.map(i -> i.toUpperCase())
                                                        .orElse("[NOOP]"));

        Optional<String> firstName = Optional.of("wdpm");
        System.out.println("Is the lastName set? " + firstName.isPresent());
        System.out.println("LastName is: " + firstName.orElseGet(() -> "[None]"));
        System.out.println("Upper lastName: " + firstName.map(i -> i.toUpperCase())
                                                         .orElse("[NOOP]"));
    }
}
