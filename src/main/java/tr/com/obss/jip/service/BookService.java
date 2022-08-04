package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto findByName(String name);

    void createNewBook(CreateNewBook createNewBookRequest);

    void updateBook(BookDto bookDto);

    void deleteBook(Long id);
}
