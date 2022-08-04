package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.create.CreateNewAuthor;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.exception.AuthorAlreadyExistException;
import tr.com.obss.jip.exception.AuthorNotFoundException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.AuthorMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.RoleService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final RequestService requestService;
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public void addNewRequest(CreateNewRequest createNewRequest) {
        Author author = getAuthenticatedAuthor();

        createNewRequest.setAuthorId(author.getId());
        requestService.createRequest(createNewRequest);
    }

    @Override
    public void createNewAuthor(CreateNewAuthor createNewAuthor) {
        final Optional<Author> existUser = authorRepository.findAuthorByUsername(createNewAuthor.getUsername());

        if (existUser.isPresent()) {
            throw new AuthorAlreadyExistException(createNewAuthor.getUsername());
        }

        Author author = authorMapper.mapTo(createNewAuthor);
        author.setPassword(passwordEncoder.encode(author.getPassword()));

        final Role standardRole = roleService.findByName(RoleType.ROLE_AUTHOR);
        author.setRoles(List.of(standardRole));

        authorRepository.save(author);
    }

    @Override
    public Author findAuthorByUsername(String username) {
        return authorRepository.findAuthorByUsername(username).orElseThrow(AuthorNotFoundException::new);
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
