package io.github.wdpm.one2many.repository;

import io.github.wdpm.one2many.domain.Book;
import org.springframework.data.repository.CrudRepository;

/**
 * @author evan
 * @date 2020/5/21
 */
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
