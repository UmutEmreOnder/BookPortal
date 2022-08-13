package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.BaseUserMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BaseUserRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BaseUserService;
import tr.com.obss.jip.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BaseUserMapper baseUserMapper;
    private final UserService userService;
    private final AuthorService authorService;

    @Override
    public void createUser(BaseUser baseUser) {
        baseUser.setPassword(passwordEncoder.encode(baseUser.getPassword()));
        baseUserRepository.save(baseUser);
    }

    @Override
    public BaseUser getUserByUsername(String username) {
        return baseUserRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public BaseUserDto findUserByUsername(String username) {
        return baseUserMapper.mapTo(getUserByUsername(username));
    }

    @Override
    public void deleteUser(long id) {
        baseUserRepository.deleteById(id);
    }

    @Override
    public <T> T getUser() {
        BaseUser baseUser = getAuthenticatedUser();

        if (baseUser instanceof User) {
            return (T) userService.findByUsername(baseUser.getUsername());
        }

        if (baseUser instanceof Author) {
            return (T) authorService.findByUsername(baseUser.getUsername());
        }

        return (T) baseUserMapper.mapTo(getAuthenticatedUser());
    }

    @Override
    public BaseUserDto findUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    private BaseUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return baseUserRepository.findUserByUsername(currentUserName).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }
}
