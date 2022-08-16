package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.BaseUserService;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final BookService bookService;
    private final BaseUserService baseUserService;

    @PutMapping("/")
    public Boolean updateUser(@RequestParam("id") Long id, @RequestBody @Valid CreateNewUser createNewUser) {
        userService.updateUserById(createNewUser, id);
        return Boolean.TRUE;
    }

    @GetMapping("/")
    public <T> T getUser() {
        return baseUserService.getUser();
    }

    @GetMapping("/book")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/like/{keyword}")
    public List<BookDto> findBooksByKeyword(@PathVariable("keyword") String keyword) {
        return bookService.findByNameContains(keyword);
    }

    @PostMapping("/book/read")
    public Boolean addReadBook(@RequestParam("name") String name) {
        userService.addReadBook(name);
        return Boolean.TRUE;
    }

    @PostMapping("/book/favorite")
    public Boolean addFavoriteBook(@RequestParam("name") String name) {
        userService.addFavoriteBook(name);
        return Boolean.TRUE;
    }

    @DeleteMapping("/book/read")
    public Boolean deleteReadBook(@RequestParam("name") String name) {
        userService.deleteReadBook(name);
        return Boolean.TRUE;
    }

    @DeleteMapping("/book/favorite")
    public Boolean deleteFavoriteBook(@RequestParam("name") String name) {
        userService.deleteFavoriteBook(name);
        return Boolean.TRUE;
    }

    @GetMapping("/book/read")
    public List<BookDto> getReadBooks() {
        return userService.getReadBooks();
    }

    @GetMapping("/book/favorite")
    public List<BookDto> getFavoriteBooks() {
        return userService.getFavoriteBooks();
    }

}
