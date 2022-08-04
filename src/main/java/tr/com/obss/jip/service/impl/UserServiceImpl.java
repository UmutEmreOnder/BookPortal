package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.CreateNewUserRequest;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.exception.UserAlreadyExistException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> getAllUsers() {
        final Iterable<User> allUsers = userRepository.findAll();

        List<UserDto> retList = new ArrayList<>();
        allUsers.forEach(user -> retList.add(userMapper.mapTo(user)));

        return retList;
    }

    @Override
    public void createNewUser(CreateNewUserRequest createNewUserRequest) {
        final Optional<User> existUser = userRepository.findUserByUsername(createNewUserRequest.getUsername());

        if (existUser.isPresent()) {
            throw new UserAlreadyExistException(createNewUserRequest.getUsername());
        }

        User user = userMapper.mapTo(createNewUserRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public UserDto findByName(String name) {
        final User user = userRepository.findUserByName(name).orElseThrow(UserNotFoundException::new);
        return userMapper.mapTo(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        return userMapper.mapTo(userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("%s is not found", username))));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException(String.format("%s is not found", username)));
    }

    @Override
    public void createUser(User adminUser) {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        userRepository.save(adminUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
