package io.github.wdpm.one2many.repository;

import io.github.wdpm.one2many.domain.Book;
import io.github.wdpm.one2many.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author evan
 * @date 2020/5/21
 */
public interface PageRepository extends CrudRepository<Page, Long> {

    List<Page> findByBook(Book book, Sort sort);
}