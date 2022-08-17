package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.dto.create.CreateNewComment;
import tr.com.obss.jip.exception.BookAlreadyExistException;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.GenreService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final GenreService genreService;

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
        final Optional<Book> bookExist = bookRepository.findBookByName(createNewBookRequest.getName());

        if (bookExist.isPresent()) {
            throw new BookAlreadyExistException(createNewBookRequest.getName());
        }

        final Genre genre = genreService.findByName(createNewBookRequest.getGenreName());

        Book book = bookMapper.mapTo(createNewBookRequest);
        book.setCreateDate(new Date());
        book.setGenre(genre);

        bookRepository.save(book);
    }

    @Override
    public void updateBook(Long id, CreateNewBook createNewBook) {
        final Book bookExist = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Genre genre = genreService.findByName(GenreType.valueOf(createNewBook.getGenreName()));

        Book book = bookMapper.mapTo(createNewBook);
        book.setId(id);
        book.setAuthor(bookExist.getAuthor());
        book.setGenre(genre);
        book.setCreateDate(bookExist.getCreateDate());
        book.setFavoriteCounter(bookExist.getFavoriteCounter());
        book.setReadCounter(bookExist.getReadCounter());

        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        Author author = book.getAuthor();

        for (User user : userRepository.findAll()) {
            user.getReadList().remove(book);
            user.getFavoriteList().remove(book);
            userRepository.save(user);
        }

        if (author != null) {
            author.getBooks().remove(book);
        }

        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> findByNameContains(String keyword) {
        final Iterable<Book> books = bookRepository.findBooksByNameContains(keyword);

        List<BookDto> retList = new ArrayList<>();
        books.forEach(book -> retList.add(bookMapper.mapTo(book)));

        return retList;
    }
}
