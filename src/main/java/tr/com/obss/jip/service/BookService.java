package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto findByName(String name);

    void createNewBook(CreateNewBook createNewBookRequest);

    void updateBook(Long id, CreateNewBook createNewBookRequest);

    void deleteBook(Long id);

    List<BookDto> findByNameContains(String keyword);
}
