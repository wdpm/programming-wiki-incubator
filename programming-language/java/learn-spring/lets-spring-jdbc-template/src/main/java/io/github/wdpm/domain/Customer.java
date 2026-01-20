package io.github.wdpm.domain;

import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/5/20
 */
public class Customer {
    private long   id;
    private String firstName;
    private String lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]").add("id=" + id)
                                                                                .add("firstName='" + firstName + "'")
                                                                                .add("lastName='" + lastName + "'")
                                                                                .toString();
    }
}
