package io.github.wdpm;

import io.github.wdpm.custom.domain.Note;
import io.github.wdpm.custom.repository.NoteRepository;
import io.github.wdpm.derived.repository.UserRepository;
import io.github.wdpm.named.domain.Book;
import io.github.wdpm.named.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

// import org.springframework.data.jpa.repository.Query;

@SpringBootApplication
public class App {

    @PersistenceContext
    private EntityManager em;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository, NoteRepository noteRepository,
                                    BookRepository bookRepository) {
        return args -> {
            // testUserRepository(userRepository);

            // testNoteRepository(noteRepository);

            testBookRepository(bookRepository);
        };
    }

    private void testBookRepository(BookRepository bookRepository) {
        // create books
        bookRepository.save(new Book("Java 101", "145804", 450));
        bookRepository.save(new Book("Spring Bot", "48524", 289));

        // execute named queries with `EntityManager`
        Query q = em.createNamedQuery("Book.findByTitleJPQL");
        q.setParameter(1, "Java 101");
        // execute query
        List<Book> books = q.getResultList();
        System.out.println(books);

        // execute native sql named query
        Query q2 = em.createNamedQuery("Book.findByIsbnNative");
        q2.setParameter("isbn", "145804");
        // execute query
        Book book = (Book) q2.getSingleResult();
        System.out.println(book);

        // list all books
        List<Book> books2 = bookRepository.findAllXML();
        System.out.println(books2);

        // fetch a single book
        Book book1 = bookRepository.findByIsbnNamedFile("145804");
        System.out.println(book1);

        // multiple parameters
        List<Book> moreBooks = bookRepository.findByTitleAndPagesGreaterThanJPQL("Spring Bot", 150);
        System.out.println(moreBooks);
    }

    private void testNoteRepository(NoteRepository noteRepository) {
        // notes sorting
        List<Note> startupNotes = noteRepository.findByTitle("startup", Sort
                .by("title")
                .ascending());
        List<Note> techNotes = noteRepository.findByTitle("tech", Sort
                .by("priority")
                .descending());
        List<Note> lengthyNotes = noteRepository.findByTitle("tech", JpaSort.unsafe("LENGTH(title)"));

        // notes pagination
        Pageable pageable = PageRequest.of(0, 10, Sort
                .by("title")
                .descending());
        Page<Note> notePage = noteRepository.findAllNotesWithPagination(pageable);
    }

    private void testUserRepository(UserRepository userRepository) {

    }

}
