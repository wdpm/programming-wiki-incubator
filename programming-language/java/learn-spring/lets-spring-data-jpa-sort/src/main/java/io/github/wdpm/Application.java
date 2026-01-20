package io.github.wdpm;

import io.github.wdpm.domain.Employee;
import io.github.wdpm.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner sortingDemo(EmployeeRepository employeeRepository) {
        return args -> {
            // create new employees
            List<Employee> list =
                    Arrays.asList(new Employee("John", "Doe", 45, 8000), new Employee("Mike", "Hassan", 55, 6500),
                                  new Employee("Emma", "Doe", 35, 4580), new Employee("Ali", "Obray", 21, 3200),
                                  new Employee("Beanca", "Lee", 21, 3200));
            employeeRepository.saveAll(list);

            // find all users
            Iterable<Employee> emps2 = employeeRepository.findAll(Sort
                                                                          .by("age", "salary")
                                                                          .descending());
            emps2.forEach(employee -> System.out.println(employee));
            System.out.println("=================");

            // find users by last name
            Sort sort2 = Sort
                    .by("salary")
                    .descending()
                    .and(Sort.by("firstName"));
            List<Employee> employees2 = employeeRepository.findByLastName("Doe", sort2);
            employees2.forEach(employee -> System.out.println(employee));
            System.out.println("=================");

            // omit sorting
            Iterable<Employee> emps = employeeRepository.findAll(Sort.unsorted());
            emps.forEach(System.out::println);
        };
    }
}
