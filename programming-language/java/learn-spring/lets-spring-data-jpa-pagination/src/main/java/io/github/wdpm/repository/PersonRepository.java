package io.github.wdpm.repository;

import io.github.wdpm.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

    // Pageable pageable = PageRequest.of(0, 10);
    // This will create a page request for the first page (page index is zero-based)
    // with 10 as the size of the page to be returned.
    // arr[0...9] elements are returned.

    Page<Person> findByLastName(String lastName, Pageable pageable);

    Slice<Person> findByAgeGreaterThan(int age, Pageable pageable);

    // derived queries

    Page<Person> findByFirstName(String firstName, Pageable pageable);

    Slice<Person> findByAgeBetween(int start, int end, Pageable pageable);

    List<Person> findByLastNameIsNotNull(Pageable pageable);

    // custom queries

    @Query("SELECT p FROM Person p WHERE p.lastName = ?1")
    Page<Person> findByLastNameJPQL(String lastName, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE p.age < :age")
    Page<Person> findByAgeLessThanJPQL(@Param("age") int page, Pageable pageable);

    // To use pagination with native SQL queries declared using the @Query annotation,
    // you need to define the count query by yourself
    @Query(value = "SELECT * FROM Person p WHERE p.firstName = :firstName",
            countQuery = "SELECT count(*) Person p WHERE p.firstName = :firstName",
            nativeQuery = true)
    Page<Person> findByFirstNameNativeSQL(@Param("firstName") String firstName, Pageable pageable);

    // named queries
    Page<Person> findByFirstNameNamed(String firstName, Pageable pageable);

    @Query(countQuery = "SELECT count(*) Person p WHERE p.lastName = :lastName",
            nativeQuery = true)
    Page<Person> findByLastNameNativeNamed(@Param("lastName") String lastName, Pageable pageable);
}
