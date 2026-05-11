package mapvsflatmap;

import java.util.Arrays;
import java.util.Objects;

@SuppressWarnings("unused")
public class Person {
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String... names) {  // String... internally is viewed an a String[]
        System.out.println("Varargs ctor, names=" + Arrays.asList(names));
        name = String.join(" ", names); // concats all strings together
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

        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
