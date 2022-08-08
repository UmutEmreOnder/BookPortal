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
import tr.com.obss.jip.mapper.AuthorMapper;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.RequestMapper;
import tr.com.obss.jip.mapper.RespondedRequestMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.RoleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

        authorRepository.save(author);
    }

    @Override
    public Author findAuthorByUsername(String username) {
        return authorRepository.findAuthorByUsername(username).orElseThrow(() -> new AuthorNotFoundException(username));
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
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

    private Author getAuthenticatedAuthor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return authorRepository.findAuthorByUsername(currentUserName).orElseThrow(AuthorNotFoundException::new);
        }

        return null;
    }
}
