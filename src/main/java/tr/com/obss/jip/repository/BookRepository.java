package tr.com.obss.jip.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByName(String name);

    List<Book> findBooksByNameContains(String keyword, Pageable pageable);

    List<Book> findBooksByAuthor(Author authenticatedAuthor, Pageable pageable);

    List<Book> findBooksByAuthorAndNameContains(Author author, String keyword, Pageable pageable);
}
