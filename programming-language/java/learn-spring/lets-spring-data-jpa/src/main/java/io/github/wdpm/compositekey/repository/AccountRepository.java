package io.github.wdpm.compositekey.repository;

import io.github.wdpm.compositekey.domain.Account;
import io.github.wdpm.compositekey.domain.AccountId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, AccountId> {

    List<Account> findByAccountType(String accountType);
}
