package io.github.wdpm.derived.repository;

import io.github.wdpm.derived.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * https://docs.spring.io/spring-data/
 * <p>
 * derived query feature: Spring Data translates it to create the required JPQL query.
 * <p>
 * JPA supported keywords, sorting, paginating, limiting the result size, and much more.
 * <p>
 * Spring Data JPA provides an extra layer of abstraction on top of an existing JPA provider like Hibernate
 */
public interface UserRepository extends CrudRepository<User, Long> {

    // Simple Methods
    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    // multiple parameters
    List<User> findByNameOrEmail(String name, String email);

    List<User> findByNameAndAge(String name, int age);

    // ActiveAndBirthDate Or NameAndAge
    List<User> findByActiveAndBirthDateOrNameAndAge(boolean active, Date dob, String name, int age);

    // equality condition keywords
    List<User> findByNameIs(String name);

    // OR
    List<User> findByNameEquals(String name);

    List<User> findByNameIsNot(String name);

    // OR
    List<User> findByNameNot(String name);

    List<User> findByEmailIsNull();

    List<User> findByEmailIsNotNull();

    List<User> findByActiveTrue();

    List<User> findByActiveFalse();

    // translate to WHERE name LIKE 'prefix%' query.
    // matching condition keywords
    List<User> findByNameStartingWith(String prefix);

    List<User> findByNameIsStartingWith(String prefix);

    List<User> findByNameStartsWith(String prefix);

    // translate to WHERE name LIKE '%suffix', can't use index
    List<User> findByNameEndingWith(String suffix);

    // translate to WHERE name LIKE '%infix%', can't use index
    List<User> findByNameContaining(String infix);

    // The Like (or NotLike) keyword does not append the % operator to the argument.
    // You have to explicitly define the matching pattern like below:
    // String pattern = "%hello%@world%";
    List<User> findByEmailLike(String pattern);

    // comparison condition keywords
    List<User> findByAgeLessThan(int age);

    List<User> findByAgeLessThanEqual(int age);

    List<User> findByAgeGreaterThan(int age);

    List<User> findByAgeGreaterThanEqual(int age);

    List<User> findByAgeBetween(int start, int end);

    List<User> findByBirthDateBefore(Date before);

    List<User> findByBirthDateAfter(Date after);

    // distinct keyword
    List<User> findDistinctByEmail(String email);

    List<User> findDistinctPeopleByNameOrEmail(String name, String email);

    // ignore case keyword
    List<User> findByNameIgnoreCase(String name);

    List<User> findByNameOrEmailAllIgnoreCase(String name, String email);

    // sorting the results
    List<User> findByNameContainingOrderByName(String name);

    List<User> findByNameContainingOrderByNameAsc(String name);

    List<User> findByNameContainingOrderByNameDesc(String name);

    // By default, the OrderBy clause sorts the results in the ascending order
    // Sort triggers the generation of an ORDER BY clause

    // sort users in descending order
    // List<User> users = userRepository.findByNameContaining("john", Sort.by("name").descending());
    //
    // multiple sort parameters
    // List<User> users = userRepository.findByNameContaining("john", Sort.by("name", "age").descending());
    List<User> findByNameContaining(String name, Sort sort);

    // limiting the results
    User findFirstByOrderByName();

    User findTopByOrderByAgeDesc();

    List<User> findFirst5ByEmail(String email);

    List<User> findDistinctTop3ByAgeLessThan(int age);

    // pagination

    // A Page knows about the total number of elements and pages available.
    // You just define the page number you want to retrieve and how many records should be on a page.

    // Pageable pageable = PageRequest.of(0, 10, Sort.by("name").descending());
    // Page<User> userPage = userRepository.findByActive(true, pageable);
    Page<User> findByActive(boolean active, Pageable pageable);

    // custom named bind parameter
    @Query("SELECT u FROM User u WHERE " + "lower(u.name) LIKE lower(CONCAT('%', :keyword, '%')) OR " +
           "lower(u.email) LIKE lower(CONCAT('%', :keyword, '%'))")
    List<User> searchUsers(@Param("keyword") String keyword);

    // delete queries
    void deleteByName(String name);

    void deleteAllByActive(boolean active);
}
