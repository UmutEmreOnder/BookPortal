package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Rating;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto findByUsername(String username);

    void addReadBook(String name);

    void addFavoriteBook(String name);

    void deleteReadBook(String name);

    void deleteFavoriteBook(String name);

    List<BookDto> getFavoriteBooks();

    List<BookDto> getReadBooks();

    BaseUserDto getUser();

    void updateUserById(CreateNewUser createNewUser, Long id);

    List<UserDto> getAllContainsUsers(String keyword);

    void addRating(Rating rate, Book book);

    void deleteUser(Long id);
}
