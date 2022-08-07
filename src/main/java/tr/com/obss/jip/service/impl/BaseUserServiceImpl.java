package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.BaseUser;
import tr.com.obss.jip.repository.BaseUserRepository;
import tr.com.obss.jip.service.BaseUserService;

@Slf4j
@RequiredArgsConstructor
@Service
public class BaseUserServiceImpl implements BaseUserService {
    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;

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
    public void deleteUser(long id) {
        baseUserRepository.deleteById(id);
    }
}
