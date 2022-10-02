package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Rating;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.service.RateService;
import tr.com.obss.jip.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final BookRepository bookRepository;
    private final UserService userService;

    @Override
    public void rateABook(Byte rate, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Rating rating = Rating
                .builder()
                .rate(rate)
                .book(book)
                .build();

        userService.addRating(rating, book);
    }

}
