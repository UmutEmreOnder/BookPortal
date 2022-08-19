package tr.com.obss.jip.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findBookByName(String name);

    Iterable<Book> findBooksByNameContains(String keyword);

    Iterable<Book> findAll(Pageable pageable);
}
