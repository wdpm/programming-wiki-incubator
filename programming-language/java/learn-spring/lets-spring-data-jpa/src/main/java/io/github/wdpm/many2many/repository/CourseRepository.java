package io.github.wdpm.many2many.repository;

import io.github.wdpm.many2many.domain.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/21
 */
public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findByTitleContaining(String title);

    // derived query methods
    List<Course> findByFeeLessThan(double fee);
}