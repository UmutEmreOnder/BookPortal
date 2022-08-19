package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.exception.BookAlreadyExistException;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Genre;
import tr.com.obss.jip.model.GenreType;
import tr.com.obss.jip.model.Rating;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.RatingRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.GenreService;
import tr.com.obss.jip.util.Helper;

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
    private final RatingRepository ratingRepository;

    @Override
    public List<BookDto> getAllBooks(Integer page, Integer pageSize, String field, String order) {
        Pageable pageable = Helper.getPagable(page, pageSize, field, order);

        final Iterable<Book> books = bookRepository.findAll(pageable);

        List<BookDto> retList = new ArrayList<>();
        books.forEach(book -> retList.add(bookMapper.mapTo(book)));

        return retList;
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
            if(rating.getBook() == book) {
                ratingRepository.delete(rating);
            }
        }

        bookRepository.delete(book);
    }

    @Override
    public List<BookDto> findByNameContains(String keyword, Integer page, Integer pageSize, String field, String order) {
        Pageable pageable = Helper.getPagable(page, pageSize, field, order);

        final List<Book> books = bookRepository.findBooksByNameContains(keyword, pageable);

        List<BookDto> retList = new ArrayList<>();
        books.forEach(book -> retList.add(bookMapper.mapTo(book)));

        return retList;
    }

    @Override
    public void addRating(Rating rate, Book book) {
        int total = book.getRate() * book.getRateCount();

        book.setRateCount(book.getRateCount() + 1);
        total += rate.getRate();
        book.setRate(total / book.getRateCount());

        bookRepository.save(book);

        ratingRepository.save(rate);
    }

    @Override
    public void updateRating(Rating rate, Rating oldRate, Book book) {
        int total = book.getRate() * book.getRateCount();

        total += (rate.getRate() - oldRate.getRate());
        book.setRate(total / book.getRateCount());

        bookRepository.save(book);
    }

    @Override
    public void deleteRate(Book book, Rating rate) {
        int total = book.getRate() * book.getRateCount();
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
