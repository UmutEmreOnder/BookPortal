package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.BaseUser;

public interface BaseUserService {
    void createUser(BaseUser adminUser);

    BaseUser getUserByUsername(String username);

    void deleteUser(long id);

    BaseUserDto getUser();

    BaseUserDto findUserByUsernameAndPassword(String username, String password);

    BaseUserDto findUserByUsername(String username);
}
