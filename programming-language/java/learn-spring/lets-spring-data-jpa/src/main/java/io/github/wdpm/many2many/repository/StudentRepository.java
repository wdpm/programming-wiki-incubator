package io.github.wdpm.many2many.repository;

import io.github.wdpm.many2many.domain.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/21
 */
public interface StudentRepository extends CrudRepository<Student, Long> {

    List<Student> findByNameContaining(String name);
}