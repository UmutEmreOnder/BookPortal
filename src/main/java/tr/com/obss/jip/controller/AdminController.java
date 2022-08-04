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
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewAuthor;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.mapper.UserMapper;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final RequestService requestService;
    private final UserMapper userMapper;

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

    // Author Part

    @PostMapping("/author/create")
    public Boolean createNewAuthor(@RequestBody @Valid CreateNewAuthor createNewAuthor) {
        authorService.createNewAuthor(createNewAuthor);

        // Authentication icin ilginc bi cozum ama ise yariyo
        userService.createNewAuthor(userMapper.mapTo(createNewAuthor));
        return Boolean.TRUE;
    }

    // Request Part
    @GetMapping("/request/all")
    public List<RequestDto> getAllRequest() {
        return requestService.getAllRequests();
    }

    @PostMapping("/request/accept")
    public Boolean acceptRequest(@RequestParam("id") Long id) {
        requestService.acceptRequest(id);
        return Boolean.TRUE;
    }

    @PostMapping("/request/deny")
    public Boolean denyRequest(@RequestParam("id") Long id) {
        requestService.denyRequest(id);
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
    public Boolean createNewBook(@RequestBody @Valid CreateNewBook createNewBookRequest) {
        bookService.createNewBook(createNewBookRequest);
        return true;
    }

    @PostMapping("/book/delete")
    public Boolean deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return Boolean.TRUE;
    }

}
