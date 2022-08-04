package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void createNewUser(CreateNewUser createNewUserRequest);

    UserDto findByName(String name);

    UserDto findByUsername(String username);

    User getUserByUsername(String username);

    void createUser(User adminUser);

    void deleteUser(Long id);

    void addReadBook(String name);

    void addFavoriteBook(String name);

    void deleteReadBook(String name);

    void deleteFavoriteBook(String name);

    void createNewAuthor(CreateNewUser mapTo);

    List<BookDto> getFavoriteBooks();

    List<BookDto> getReadBooks();
}
