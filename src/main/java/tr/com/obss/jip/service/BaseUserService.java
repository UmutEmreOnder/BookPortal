package tr.com.obss.jip.service;

import tr.com.obss.jip.model.BaseUser;

public interface BaseUserService {
    void createUser(BaseUser adminUser);

    BaseUser getUserByUsername(String username);
}
