package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Rating;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers(String keyword, Integer page, Integer pageSize, String field, String order);

    UserDto findByUsername(String username);

    void addReadBook(Long id);

    void addFavoriteBook(Long id);

    void deleteReadBook(Long id);

    void deleteFavoriteBook(Long id);

    List<BookDto> getFavoriteBooks();

    List<BookDto> getReadBooks();

    BaseUserDto getUser();

    void updateUserById(CreateNewUser createNewUser, Long id);

    void addRating(Rating rate, Book book);

    void deleteUser(Long id);

    Byte getRate(Long bookId);

    Long getCount();
}
