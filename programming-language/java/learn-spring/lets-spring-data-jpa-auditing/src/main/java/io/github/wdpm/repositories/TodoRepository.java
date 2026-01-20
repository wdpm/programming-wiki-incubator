package io.github.wdpm.repositories;

import io.github.wdpm.domain.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    //add queries
}
