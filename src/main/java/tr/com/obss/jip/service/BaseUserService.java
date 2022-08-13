package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.model.BaseUser;

public interface BaseUserService {
    void createUser(BaseUser adminUser);

    BaseUser getUserByUsername(String username);

    void deleteUser(long id);

    <T> T getUser();

    BaseUserDto findUserByUsernameAndPassword(String username, String password);

    BaseUserDto findUserByUsername(String username);
}
