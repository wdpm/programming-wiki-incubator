package io.github.wdpm;

import io.github.wdpm.compositekey.domain.Account;
import io.github.wdpm.compositekey.domain.AccountId;
import io.github.wdpm.compositekey.domain.Employee;
import io.github.wdpm.compositekey.domain.EmployeeId;
import io.github.wdpm.compositekey.repository.AccountRepository;
import io.github.wdpm.compositekey.repository.EmployeeRepository;
import io.github.wdpm.many2many.domain.Course;
import io.github.wdpm.many2many.domain.Student;
import io.github.wdpm.many2many.repository.CourseRepository;
import io.github.wdpm.many2many.repository.StudentRepository;
import io.github.wdpm.one2many.domain.Book;
import io.github.wdpm.one2many.domain.Page;
import io.github.wdpm.one2many.repository.BookRepository;
import io.github.wdpm.one2many.repository.PageRepository;
import io.github.wdpm.one2one.domain.Address;
import io.github.wdpm.one2one.domain.User;
import io.github.wdpm.one2one.repository.AddressRepository;
import io.github.wdpm.one2one.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author evan
 * @date 2020/5/21
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(UserRepository userRepository, AddressRepository addressRepository,
                                         BookRepository bookRepository, PageRepository pageRepository,
                                         StudentRepository studentRepository, CourseRepository courseRepository,
                                         AccountRepository accountRepository, EmployeeRepository employeeRepository) {
        return args -> {
            // one2one(userRepository);

            // one2many(bookRepository, pageRepository);

            // many2many(studentRepository, courseRepository);

            compositeKey(accountRepository, employeeRepository);
        };
    }

    private void compositeKey(AccountRepository accountRepository, EmployeeRepository employeeRepository) {
        // ======= `@IdClass` Annotation =======

        // create new accounts
        accountRepository.save(new Account("458666", "Checking", 4588));
        accountRepository.save(new Account("458689", "Checking", 2500));
        accountRepository.save(new Account("424265", "Saving", 100000));

        // fetch accounts by a given type
        List<Account> accounts = accountRepository.findByAccountType("Checking");
        accounts.forEach(System.out::println);

        // fetch account by composite key
        Optional<Account> account = accountRepository.findById(new AccountId("424265", "Saving"));
        account.ifPresent(System.out::println);

        // ======= `@EmbeddedId` Annotation =======

        // create new employees
        employeeRepository.save(new Employee(new EmployeeId(100L, 10L), "John Doe", "john@example.com", "123456"));
        employeeRepository.save(new Employee(new EmployeeId(101L, 20L), "Emma Ali", "emma@example.com", "654321"));

        // fetch employees by a given department id
        List<Employee> employees = employeeRepository.findByEmployeeIdDepartmentId(20L);
        employees.forEach(System.out::println);

        // fetch employee by composite key
        Optional<Employee> employee = employeeRepository.findById(new EmployeeId(100L, 10L));
        employee.ifPresent(System.out::println);
    }

    private void many2many(StudentRepository studentRepository, CourseRepository courseRepository) {
        // create a student
        Student student = new Student("John Doe", 15, "8th");
        // save the student
        studentRepository.save(student);

        // create three courses
        Course course1 = new Course("Machine Learning", "ML", 12, 1500);
        Course course2 = new Course("Database Systems", "DS", 8, 800);
        Course course3 = new Course("Web Basics", "WB", 10, 0);
        // save courses
        courseRepository.saveAll(Arrays.asList(course1, course2, course3));

        // add courses to the student
        student
                .getCourses()
                .addAll(Arrays.asList(course1, course2, course3));
        // update the student
        studentRepository.save(student);
    }

    private void one2many(BookRepository bookRepository, PageRepository pageRepository) {
        // create a new book
        Book book = new Book("Java Code", "Some one", "123456");

        // save the book
        bookRepository.save(book);

        // create and save new pages
        pageRepository.save(new Page(1, "Introduction contents", "Introduction", book));
        pageRepository.save(new Page(23, "Java 8 contents", "Java 8", book));
        pageRepository.save(new Page(46, "Concurrency contents", "Concurrency", book));
    }

    private void one2one(UserRepository userRepository) {
        // create a user instance
        User user = new User("John Doe", "john.doe@example.com", "1234abcd");

        // create an address instance
        Address address = new Address("Lake View 321", "Berlin", "Berlin", "95781", "DE");

        // set child reference
        address.setUser(user);

        // set parent reference
        user.setAddress(address);

        // save the parent
        // which will save the child (address) as well
        userRepository.save(user);
    }
}
