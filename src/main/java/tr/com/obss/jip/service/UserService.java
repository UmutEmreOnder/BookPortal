package tr.com.obss.jip.service;

import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.User;

import javax.servlet.http.HttpServletRequest;
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
}
