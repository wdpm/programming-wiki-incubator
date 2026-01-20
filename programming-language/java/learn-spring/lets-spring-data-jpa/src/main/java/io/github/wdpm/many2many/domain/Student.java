package io.github.wdpm.many2many.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * 多对多关系的典型示例是学生与课程之间的关系。一个学生可以注册多个课程，一个课程也可以有多个学生。
 *
 * @author evan
 * @date 2020/5/21
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    @Id
    //declared that the primary key should be an AUTO INCREMENT field.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String grade;

    // 在双向关系中，在两个实体中都定义了@ManyToMany批注，但是只有一个实体可以拥有该关系。
    // 这里选择了Student类作为关系的所有者。
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    // @JoinTable批注定义两个实体之间的联接表
    // what is the meaning of updatable keyword?
    @JoinTable(name = "student_course",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false,
                    updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false,
                    updatable = false)})
    private Set<Course> courses = new HashSet<>();

    public Student() {
    }

    public Student(String name, int age, String grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("grade='" + grade + "'")
                .add("courses=" + courses)
                .toString();
    }
}