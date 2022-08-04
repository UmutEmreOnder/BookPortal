package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.CreateNewBookRequest;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto findByName(String name);

    void createNewBook(CreateNewBookRequest createNewBookRequest);

    void updateBook(BookDto bookDto);

    void deleteBook(Long id);
}
