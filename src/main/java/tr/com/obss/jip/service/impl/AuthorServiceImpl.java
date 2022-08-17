package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.AuthorDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.exception.AuthorAlreadyExistException;
import tr.com.obss.jip.exception.AuthorNotFoundException;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.mapper.AuthorMapper;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.RequestMapper;
import tr.com.obss.jip.mapper.RespondedRequestMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.repository.BaseUserRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.RoleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RequestMapper requestMapper;
    private final BookMapper bookMapper;
    private final RespondedRequestMapper respondedRequestMapper;
    private final RequestService requestService;
    private final BaseUserRepository baseUserRepository;
    private final BookService bookService;

    @Override
    public void addNewRequest(CreateNewRequest createNewRequest) {
        requestService.addNewRequest(createNewRequest, getAuthenticatedAuthor());
    }

    @Override
    public void createNewAuthor(CreateNewUser createNewAuthor) {
        final Optional<Author> existUser = authorRepository.findAuthorByUsername(createNewAuthor.getUsername());

        if (existUser.isPresent()) {
            throw new AuthorAlreadyExistException(createNewAuthor.getUsername());
        }

        Author author = authorMapper.mapTo(createNewAuthor);
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setCreateDate(new Date());

        final Role standardRole = roleService.findByName(RoleType.ROLE_AUTHOR);
        author.setRoles(List.of(standardRole));
        author.setEnabled(true);

        authorRepository.save(author);
    }

    @Override
    public List<RequestDto> getAllRequests() {
        return getAuthenticatedAuthor().getAddingBookRequests().stream().map(requestMapper::mapTo).toList();
    }

    @Override
    public List<BookDto> getAllBooks() {
        return getAuthenticatedAuthor().getBooks().stream().map(bookMapper::mapTo).toList();
    }

    @Override
    public List<RespondedRequestDto> getAllRespondedRequests() {
        return getAuthenticatedAuthor().getRespondedBookRequests().stream().map(respondedRequestMapper::mapTo).toList();
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        Iterable<Author> authors = authorRepository.findAll();

        List<AuthorDto> authorDtos = new ArrayList<>();
        authors.forEach(author -> authorDtos.add(authorMapper.mapTo(author)));

        return authorDtos;
    }

    @Override
    public void updateAuthor(Long id, CreateNewUser createNewAuthor) {
        final Author authorExist = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        isUnameEmailUnique(createNewAuthor, authorExist);

        Author author = authorMapper.mapTo(createNewAuthor);
        transferFields(author, authorExist);

        authorRepository.save(authorExist);
    }

    private void isUnameEmailUnique(CreateNewUser createNewUser, Author authorExist) {
        final Optional<BaseUser> existUser1 = authorExist.getUsername().equals(createNewUser.getUsername()) ? Optional.empty() : baseUserRepository.findUserByUsername(createNewUser.getUsername());
        final Optional<BaseUser> existUser2 = authorExist.getEmail().equals(createNewUser.getEmail()) ? Optional.empty() : baseUserRepository.findUserByEmail(createNewUser.getEmail());

        if (existUser1.isPresent() || existUser2.isPresent()) {
            throw new UserAlreadyExistException(createNewUser.getUsername());
        }
    }

    @Override
    public void deleteAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));

        for (Book book : author.getBooks()) {
            bookService.deleteBook(book.getId());
        }

        authorRepository.deleteById(id);
    }

    @Override
    public List<BookDto> findByNameContains(String keyword) {
        return Objects.requireNonNull(getAuthenticatedAuthor()).getBooks().stream().filter(book -> book.getName().contains(keyword)).map(bookMapper::mapTo).toList();
    }

    @Override
    public List<AuthorDto> getAllAuthorsContains(String keyword) {
        final Iterable<Author> allUsers = authorRepository.findAuthorsByNameContains(keyword);

        List<AuthorDto> retList = new ArrayList<>();
        allUsers.forEach(author -> retList.add(authorMapper.mapTo(author)));

        return retList;
    }

    @Override
    public AuthorDto findByUsername(String username) {
        return authorMapper.mapTo(authorRepository.findAuthorByUsername(username).orElseThrow(() -> new AuthorNotFoundException(username)));
    }

    private void transferFields(Author author, Author authorExist) {
        authorExist.setName(author.getName());
        authorExist.setSurname(author.getSurname());
        authorExist.setEmail(author.getEmail());
        authorExist.setAge(author.getAge());
        authorExist.setUsername(author.getUsername());
    }

    private Author getAuthenticatedAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return authorRepository.findAuthorByUsername(currentUserName).orElseThrow(AuthorNotFoundException::new);
        }

        return null;
    }
}
