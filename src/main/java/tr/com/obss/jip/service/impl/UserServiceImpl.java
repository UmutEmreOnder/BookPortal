package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RoleService;
import tr.com.obss.jip.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final BookMapper bookMapper;


    public List<UserDto> getAllUsers() {
        final Iterable<User> allUsers = userRepository.findAll();

        List<UserDto> retList = new ArrayList<>();
        allUsers.forEach(user -> retList.add(userMapper.mapTo(user)));

        return retList;
    }

    @Override
    public void createNewUser(CreateNewUser createNewUserRequest) {
        final Optional<User> existUser = userRepository.findUserByUsername(createNewUserRequest.getUsername());

        if (existUser.isPresent()) {
            throw new UserAlreadyExistException(createNewUserRequest.getUsername());
        }

        User user = userMapper.mapTo(createNewUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        final Role standardRole = roleService.findByName(RoleType.ROLE_USER);
        user.setRoles(List.of(standardRole));

        userRepository.save(user);
    }

    @Override
    public UserDto findByName(String name) {
        final User user = userRepository.findUserByName(name).orElseThrow(() -> new UserNotFoundException(name));
        return userMapper.mapTo(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.mapTo(userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("%s is not found", username))));
    }

    @Override
    public void addReadBook(String name) {
        User user = getAuthenticatedUser();
        addBookToList(name, user.getReadList());
        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(String name) {
        User user = getAuthenticatedUser();
        addBookToList(name, user.getFavoriteList());
        userRepository.save(user);
    }

    private void addBookToList(String bookName, List<Book> list) {
        Book book = bookRepository.findBookByName(bookName).orElseThrow(() -> new BookNotFoundException(bookName));
        list.add(book);
    }

    @Override
    public void deleteReadBook(String name) {
        User user = getAuthenticatedUser();
        deleteBookFromList(name, user.getReadList());
        userRepository.save(user);
    }

    @Override
    public void deleteFavoriteBook(String name) {
        User user = getAuthenticatedUser();
        deleteBookFromList(name, user.getFavoriteList());
        userRepository.save(user);
    }

    private void deleteBookFromList(String bookName, List<Book> list) {
        Book book = bookRepository.findBookByName(bookName).orElseThrow(() -> new BookNotFoundException(bookName));
        list.remove(book);
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

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userRepository.findUserByUsername(currentUserName).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }
}
