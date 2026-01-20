package io.github.wdpm.one2one.repository;

import io.github.wdpm.one2one.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
