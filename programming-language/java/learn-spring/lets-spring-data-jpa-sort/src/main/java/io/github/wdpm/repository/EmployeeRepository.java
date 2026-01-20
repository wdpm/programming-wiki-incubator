package io.github.wdpm.repository;

import io.github.wdpm.domain.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    // static sorting
    List<Employee> findByOrderBySalaryAsc();

    List<Employee> findByOrderBySalaryDesc();

    List<Employee> findByLastNameOrderByAgeDesc(String lastName);

    List<Employee> findByOrderByLastNameAscSalaryDesc();

    @Query("SELECT e FROM Employee e WHERE e.salary > ?1 ORDER BY e.age DESC")
    List<Employee> findBySalaryGreaterThanJPQL(double salary);

    @Query(value = "SELECT * FROM Employee e WHERE e.fistName = :firstName ORDER BY e.salary ASC",
            nativeQuery = true)
    List<Employee> findByFirstNameNativeSQL(@Param("firstName") String firstName);

    List<Employee> findByAgeGreaterThanNamedJPQL(@Param("age") int age);

    @Query(nativeQuery = true)
    List<Employee> findAllNamedNativeSQL();

    // dynamic sorting
    // with Sort parameter
    List<Employee> findByLastName(String lastName, Sort sort);

    List<Employee> findByFirstNameAndSalaryLessThan(String lastName, double salary, Sort sort);

    @Query("SELECT e FROM Employee e WHERE e.salary > ?1 AND e.salary < ?2")
    List<Employee> findBySalaryRange(double start, double end, Sort sort);

    //Spring Data JPA does not support dynamic sorting for native SQL queries
}
