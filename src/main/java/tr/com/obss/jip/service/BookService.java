package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Rating;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto findByName(String name);

    void createNewBook(CreateNewBook createNewBookRequest);

    void updateBook(Long id, CreateNewBook createNewBookRequest);

    void deleteBook(Long id);

    List<BookDto> findByNameContains(String keyword);

    void addRating(Rating rate, Book book);

    void updateRating(Rating rate, Rating oldRate, Book book);

    void deleteRate(Book book, Rating rating);

    BookDto findById(Long id);
}
