package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Comment;
import tr.com.obss.jip.model.Rating;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BaseUserRepository;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.UserService;
import tr.com.obss.jip.util.Helper;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BaseUserRepository baseUserRepository;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    private final BookService bookService;
    private final EntityManager entityManager;

    @Override
    public List<UserDto> getAllUsers(String keyword, Integer page, Integer pageSize, String field, String order) {
        List<User> userList = Helper.getAll(entityManager, "name", keyword, page, pageSize, field, order, User.class);

        return userList.stream().map(userMapper::mapTo).toList();
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.mapTo(userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("%s is not found", username))));
    }

    @Override
    public void addReadBook(Long id) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setReadCounter(book.getReadCounter() + 1);
        user.getReadList().add(book);

        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(Long id) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));;
        book.setFavoriteCounter(book.getFavoriteCounter() + 1);
        user.getFavoriteList().add(book);

        userRepository.save(user);
    }

    @Override
    public void deleteReadBook(Long id) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setReadCounter(book.getReadCounter() - 1);
        user.getReadList().remove(book);

        userRepository.save(user);
    }

    @Override
    public void deleteFavoriteBook(Long id) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        book.setFavoriteCounter(book.getFavoriteCounter() - 1);
        user.getFavoriteList().remove(book);

        userRepository.save(user);
    }

    @Override
    public List<BookDto> getFavoriteBooks() {
        User user = getAuthenticatedUser();
        return user.getFavoriteList().stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public List<BookDto> getReadBooks() {
        User user = getAuthenticatedUser();
        return user.getReadList().stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public BaseUserDto getUser() {
        return userMapper.mapUserToBase(getAuthenticatedUser());
    }

    @Override
    public void updateUserById(CreateNewUser createNewUser, Long id) {
        final User userExist = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        isUnameEmailUnique(createNewUser, userExist);
        transferFields(createNewUser, userExist);

        userRepository.save(userExist);
    }

    @Override
    public void addRating(Rating rate, Book book) {
        User user = getAuthenticatedUser();

        if (user.getRates().containsKey(book)) {
            bookService.updateRating(rate, user.getRates().get(book), book);
            user.getRates().replace(book, rate);
            userRepository.save(user);
        } else {
            bookService.addRating(rate, book);
            user.getRates().put(book, rate);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        for(Book book : bookRepository.findAll()) {
            if (user.getRates().containsKey(book)) {
                bookService.deleteRate(book, user.getRates().get(book));
            }

            book.getComments().removeIf(comment -> comment.getUser() == user);
            bookRepository.save(book);
        }

        userRepository.delete(user);
    }

    @Override
    public Byte getRate(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        if (getAuthenticatedUser().getRates().containsKey(book)) {
            return getAuthenticatedUser().getRates().get(book).getRate();
        }

        return 0;
    }

    @Override
    public Long getCount() {
        return userRepository.count();
    }

    private void isUnameEmailUnique(CreateNewUser createNewUser, User userExist) {
        final Optional<BaseUser> existUser1 = userExist.getUsername().equals(createNewUser.getUsername()) ? Optional.empty() : baseUserRepository.findUserByUsername(createNewUser.getUsername());
        final Optional<BaseUser> existUser2 = userExist.getEmail().equals(createNewUser.getEmail()) ? Optional.empty() : baseUserRepository.findUserByEmail(createNewUser.getEmail());

        if (existUser1.isPresent() || existUser2.isPresent()) {
            throw new UserAlreadyExistException(createNewUser.getUsername());
        }
    }

    private void transferFields(CreateNewUser user, User userExists) {
        userExists.setName(user.getName());
        userExists.setSurname(user.getSurname());
        userExists.setEmail(user.getEmail());
        userExists.setAge(user.getAge());
        userExists.setUsername(user.getUsername());
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
