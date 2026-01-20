package io.github.wdpm.repository;

import io.github.wdpm.domain.Movie;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    // add queries
}
