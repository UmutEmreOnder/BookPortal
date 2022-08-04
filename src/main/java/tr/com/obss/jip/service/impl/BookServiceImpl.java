package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.exception.BookAlreadyExistException;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorService authorService;

    @Override
    public List<BookDto> getAllBooks() {
        final Iterable<Book> books = bookRepository.findAll();

        List<BookDto> retList = new ArrayList<>();
        books.forEach(book -> retList.add(bookMapper.mapTo(book)));

        return retList;
    }

    @Override
    public BookDto findByName(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow(() -> new BookNotFoundException(name));
        return bookMapper.mapTo(book);
    }

    @Override
    public void createNewBook(CreateNewBook createNewBookRequest) {
        final Optional<Book> existBook = bookRepository.findBookByName(createNewBookRequest.getName());

        if (existBook.isPresent()) {
            throw new BookAlreadyExistException(createNewBookRequest.getName());
        }

        Book book = bookMapper.mapTo(createNewBookRequest);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        // ?
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Iterable<Author> authors = authorService.findAll();
        for (Author author : authors) {
            author.getBooks().remove(book);
        }

        bookRepository.delete(book);
    }

    @Override
    public Optional<Book> findBookByName(String name) {
        return bookRepository.findBookByName(name);
    }
}
