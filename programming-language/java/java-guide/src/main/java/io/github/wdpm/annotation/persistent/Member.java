package io.github.wdpm.annotation.persistent;

/**
 * @author evan
 * @date 2020/5/2
 */
@DBTable(name = "member")
public class Member {
    @SQLString(30)
    String  firstName;

    @SQLString(50)
    String  lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    String reference;

    static int memberCount;

    @Override
    public String toString() {
        return reference;
    }

    public String getReference() {
        return reference;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }
}