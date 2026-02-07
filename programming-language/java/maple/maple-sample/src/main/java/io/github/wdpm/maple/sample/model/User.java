package io.github.wdpm.maple.sample.model;

import java.util.Date;
import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/4/22
 */
public class User {
    private static final long    serialVersionUID = 9055514032155274125L;
    private              Long    id;
    private              String  name;
    private              Integer age;
    private              Date    birthday;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("birthday=" + birthday)
                .toString();
    }
}
