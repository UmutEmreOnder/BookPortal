package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.CreateNewBookRequest;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final BookService bookService;

    // User Part
    @GetMapping("/user/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{username}")
    public UserDto findUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/user/delete")
    public Boolean deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return Boolean.TRUE;
    }


    // Book Part
    @GetMapping("/book/all")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{name}")
    public BookDto findBookByName(@PathVariable("name") String name) {
        return bookService.findByName(name);
    }

    @PostMapping("/book/create")
    public Boolean createNewBook(@RequestBody @Valid CreateNewBookRequest createNewBookRequest) {
        bookService.createNewBook(createNewBookRequest);
        return true;
    }

}
