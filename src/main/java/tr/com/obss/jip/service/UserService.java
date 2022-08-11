package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void createNewUser(CreateNewUser createNewUserRequest);

    UserDto findByName(String name);

    UserDto findByUsername(String username);

    void addReadBook(String name);

    void addFavoriteBook(String name);

    void deleteReadBook(String name);

    void deleteFavoriteBook(String name);

    List<BookDto> getFavoriteBooks();

    List<BookDto> getReadBooks();

    void updateUser(Long id, CreateNewUser createNewUser);

    void updateUser(CreateNewUser createNewUser);

    BaseUserDto getUser();

    void updateUserById(CreateNewUser createNewUser, Long id);
}
