package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.User;

public interface RegisterService {
    void createNewUser(CreateNewUser createNewUserRequest);

    void sendRegistrationConfirmationEmail(User user);

    Boolean verifyToken(String token);

    void sendNewEmail(String email);
}
