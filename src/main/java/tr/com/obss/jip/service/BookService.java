package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto findByName(String name);

    void createNewBook(CreateNewBook createNewBookRequest);

    void updateBook(BookDto bookDto);

    void deleteBook(Long id);

    Optional<Book> findBookByName(String name);
}
