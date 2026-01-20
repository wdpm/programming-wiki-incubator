package mapvsflatmap;

import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String... names) {
        System.out.println("Varargs ctor, names=" + Arrays.asList(names));
        name = Arrays.stream(names)
                .collect(Collectors.joining(" ")); // concats all strings together
    }

    public Person(Person p) {
        this.name = p.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("mapvsflatmap.Person(%s)", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return name != null ? name.equals(person.name) : person.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
