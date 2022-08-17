package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Rating;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.RatingRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RateService;
import tr.com.obss.jip.service.UserService;

@Service
@Slf4j
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public void rateABook(Byte rate, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Rating rating = Rating
                .builder()
                .rate(rate)
                .book(book)
                .user(getAuthenticatedUser())
                .build();

        userService.addRating(rating, book);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userRepository.findUserByUsername(currentUserName).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }
}
