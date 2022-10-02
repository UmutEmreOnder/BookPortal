package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.exception.BookAlreadyExistException;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.model.*;
import tr.com.obss.jip.repository.*;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.GenreService;
import tr.com.obss.jip.util.Helper;

import javax.persistence.EntityManager;
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
    private final RatingRepository ratingRepository;
    private final EntityManager entityManager;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<BookDto> getAllBooks(String keyword, Integer page, Integer pageSize, String field, String order, List<String> authors, List<String> genres) {
        List<Book> bookList = Helper.getBooksOrRequests(entityManager, "name", keyword, page, pageSize, field, order, Book.class, authors, authorRepository, genres, genreRepository);
        return bookList.stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public Long getCount() {
        return bookRepository.count();
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
            user.getRates().remove(book);
            user.getComments().removeIf(comment -> comment.getBook() == book);
            userRepository.save(user);
        }

        if (author != null) {
            author.getBooks().remove(book);
        }

        for (Rating rating : ratingRepository.findAll()) {
            if (rating.getBook() == book) {
                ratingRepository.delete(rating);
            }
        }

        bookRepository.delete(book);
    }

    @Override
    public void addRating(Rating rate, Book book) {
        double total = book.getRate() * book.getRateCount();

        book.setRateCount(book.getRateCount() + 1);
        total += rate.getRate();

        double newRate = total / book.getRateCount();
        book.setRate(newRate);

        bookRepository.save(book);
        ratingRepository.save(rate);
    }

    @Override
    public void updateRating(Rating rate, Rating oldRate, Book book) {
        double total = book.getRate() * book.getRateCount();

        total += (rate.getRate() - oldRate.getRate());
        book.setRate(total / book.getRateCount());

        bookRepository.save(book);
    }

    @Override
    public void deleteRate(Book book, Rating rate) {
        double total = book.getRate() * book.getRateCount();
        total -= (rate.getRate());
        book.setRateCount(book.getRateCount() - 1);

        book.setRate(book.getRateCount() == 0 ? 0 : total / book.getRateCount());

        ratingRepository.delete(rate);
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.mapTo(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id)));
    }


}
