package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.dto.*;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.*;

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
    private final CommentService commentService;

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


    @DeleteMapping("/comment")
    public Boolean deleteComment(@RequestParam("id") Long id) {
        return commentService.deleteCommentAdmin(id);
    }
}
