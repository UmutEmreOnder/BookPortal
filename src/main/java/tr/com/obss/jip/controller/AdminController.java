package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.AuthorDto;
import tr.com.obss.jip.dto.BaseUserDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.UserDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BaseUserService;
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
    private final BaseUserService baseUserService;

    // User Part
    @GetMapping("/user")
    public List<UserDto> getAllUsers(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order) {
        return userService.getAllUsers(keyword, page, pageSize, field, order);
    }

    @GetMapping("/user/count")
    public Long getUserCount() {
        return userService.getCount();
    }


    @GetMapping("/user/{username}")
    public UserDto findUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/baseuser/{username}")
    public BaseUserDto findBaseUserByUsername(@PathVariable("username") String username) {
        return baseUserService.findUserByUsername(username);
    }

    @DeleteMapping("/user")
    public Boolean deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return Boolean.TRUE;
    }

    @PutMapping("/user")
    public Boolean updateUser(@RequestParam("id") Long id, @RequestBody @Valid CreateNewUser createNewUser) {
        userService.updateUserById(createNewUser, id);
        return Boolean.TRUE;
    }

    // Author Part

    @PostMapping("/author")
    public Boolean createNewAuthor(@RequestBody @Valid CreateNewUser createNewAuthor) {
        authorService.createNewAuthor(createNewAuthor);
        return Boolean.TRUE;
    }

    @PutMapping("/author")
    public Boolean updateAuthor(@RequestParam("id") Long id, @RequestBody @Valid CreateNewUser createNewAuthor) {
        authorService.updateAuthor(id, createNewAuthor);
        return Boolean.TRUE;
    }

    @GetMapping("/author")
    public List<AuthorDto> getAllAuthors(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order) {
        return authorService.getAllAuthors(keyword, page, pageSize, field, order);
    }

    @GetMapping("/author/count")
    public Long getAuthorCount() {
        return authorService.getCount();
    }


    @DeleteMapping("/author")
    public Boolean deleteAuthor(@RequestParam("id") Long id) {
        authorService.deleteAuthorById(id);
        return Boolean.TRUE;
    }


    // AddingBookRequest Part
    @GetMapping("/request")
    public List<RequestDto> getAllRequest(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order, @RequestParam(value = "author", required = false) List<String> authors, @RequestParam(value = "genre", required = false) List<String> genres) {
        return requestService.getAllRequests(page, pageSize, field, order, authors, genres);
    }

    @GetMapping("/request/count")
    public Long getRequestCount() {
        return requestService.getCount();
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

    @GetMapping("/book/{name}")
    public BookDto findBookByName(@PathVariable("name") String name) {
        return bookService.findByName(name);
    }

    @PostMapping("/book")
    public Boolean createNewBook(@RequestBody @Valid CreateNewBook createNewBookRequest) {
        bookService.createNewBook(createNewBookRequest);
        return true;
    }

    @DeleteMapping("/book")
    public Boolean deleteBook(@RequestParam("id") Long id) {
        bookService.deleteBook(id);
        return Boolean.TRUE;
    }

    @PutMapping("/book")
    public Boolean updateBook(@RequestParam("id") Long id, @RequestBody @Valid CreateNewBook createNewBook) {
        bookService.updateBook(id, createNewBook);
        return Boolean.TRUE;
    }
}
