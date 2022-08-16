package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.obss.jip.dto.AccountVerificationEmailContext;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.exception.InvalidTokenException;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.model.SecureToken;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BaseUserRepository;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.SecureTokenRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.EmailService;
import tr.com.obss.jip.service.RoleService;
import tr.com.obss.jip.service.SecureTokenService;
import tr.com.obss.jip.service.UserService;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BaseUserRepository baseUserRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final BookMapper bookMapper;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    private static final String REDIRECT_LOGIN= "http://localhost:3000/";
    private final MessageSource messageSource;
    @Value("${site.base.url.https}")
    private String baseURL;

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

        User user = registerUser(createNewUserRequest);
        userRepository.save(user);

        sendRegistrationConfirmationEmail(user);
    }

    private User registerUser(CreateNewUser createNewUserRequest) {
        User user = userMapper.mapTo(createNewUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreateDate(new Date());

        final Role standardRole = roleService.findByName(RoleType.ROLE_USER);
        user.setRoles(List.of(standardRole));
        user.setEnabled(false);

        return user;
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.mapTo(userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("%s is not found", username))));
    }

    @Override
    public void addReadBook(String name) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findBookByName(name).orElseThrow(() -> new BookNotFoundException(name));
        book.setReadCounter(book.getReadCounter() + 1);
        user.getReadList().add(book);

        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(String name) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findBookByName(name).orElseThrow(() -> new BookNotFoundException(name));
        book.setFavoriteCounter(book.getFavoriteCounter() + 1);
        user.getFavoriteList().add(book);

        userRepository.save(user);
    }

    @Override
    public void deleteReadBook(String name) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findBookByName(name).orElseThrow(() -> new BookNotFoundException(name));
        book.setReadCounter(book.getReadCounter() - 1);
        user.getReadList().remove(book);

        userRepository.save(user);
    }

    @Override
    public void deleteFavoriteBook(String name) {
        User user = getAuthenticatedUser();

        Book book = bookRepository.findBookByName(name).orElseThrow(() -> new BookNotFoundException(name));
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
    public List<UserDto> getAllContainsUsers(String keyword) {
        final Iterable<User> allUsers = userRepository.findUserByNameContains(keyword);

        List<UserDto> retList = new ArrayList<>();
        allUsers.forEach(user -> retList.add(userMapper.mapTo(user)));

        return retList;
    }

    @Override
    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);

        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public String verifyToken(String token){
        try {
            verifyUser(token);
        } catch (InvalidTokenException e) {
            return "The token is expired or incorrect!";
        }

        return "You have successfully verified your account!";
    }

    @Override
    public boolean verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);

        if(Objects.isNull(secureToken) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }

        User user = userRepository.findById(secureToken.getUser().getId()).orElseThrow(UserNotFoundException::new);

        if(Objects.isNull(user)){
            return false;
        }

        user.setEnabled(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
        return true;
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
