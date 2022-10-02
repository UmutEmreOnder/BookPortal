package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.exception.InvalidTokenException;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mail.AccountVerificationEmailContext;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.model.Role;
import tr.com.obss.jip.model.RoleType;
import tr.com.obss.jip.model.SecureToken;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.SecureTokenRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.EmailService;
import tr.com.obss.jip.service.RegisterService;
import tr.com.obss.jip.service.RoleService;
import tr.com.obss.jip.service.SecureTokenService;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailService emailService;
    @Value("${site.base.url.https}")
    private String baseURL;

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
    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);

        sendEmail(user, secureToken);
    }

    @Override
    public Boolean verifyToken(String token) {
        try {
            verifyUser(token);
        } catch (InvalidTokenException e) {
            return false;
        }

        return true;
    }

    @Override
    public void sendNewEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        if (Boolean.TRUE.equals(user.getEnabled())) {
            throw new UserAlreadyExistException(email);
        }

        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenService.saveSecureToken(secureToken);

        sendEmail(user, secureToken);
    }

    private void sendEmail(User user, SecureToken secureToken) {
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

    private void verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token).orElseThrow(() -> new InvalidTokenException(token));

        if (secureToken.getExpireAt().isBefore(LocalDateTime.now())) {
            secureTokenRepository.delete(secureToken);
            throw new InvalidTokenException("Token is not valid");
        }

        User user = userRepository.findById(secureToken.getUser().getId()).orElseThrow(UserNotFoundException::new);

        user.setEnabled(true);
        userRepository.save(user);

        secureTokenService.removeToken(secureToken);
    }
}
