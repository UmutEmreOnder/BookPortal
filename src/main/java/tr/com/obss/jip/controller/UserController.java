package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.CommentDto;
import tr.com.obss.jip.dto.create.CreateNewComment;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.*;

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
    private final CommentService commentService;
    private final RateService rateService;

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
    public List<BookDto> getAllBooks(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order, @RequestParam(value = "author", required = false) List<String> authors, @RequestParam(value = "genre", required = false) List<String> genres) {
        return bookService.getAllBooks(keyword, page, pageSize, field, order, authors, genres);
    }

    @GetMapping("/book/count")
    public Long getBookCount() {
        return bookService.getCount();
    }

    @GetMapping("/book/{id}")
    public BookDto getBookById(@PathVariable("id") Long id) {
        return bookService.findById(id);
    }

    @PostMapping("/comment")
    public Boolean addComment(@RequestParam("bookId") Long bookId, @RequestBody @Valid CreateNewComment comment) {
        commentService.addComment(bookId, comment);
        return Boolean.TRUE;
    }

    @GetMapping("/comment")
    public List<CommentDto> getComments(@RequestParam("bookId") Long bookId, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize) {
        return commentService.getComments(bookId, page, pageSize);
    }

    @DeleteMapping("/comment")
    public Boolean deleteComment(@RequestParam("id") Long id) {
        return commentService.deleteComment(id);
    }

    @PostMapping("/rate/{bookId}")
    public Boolean rateABook(@RequestParam("rate") Byte rate, @PathVariable("bookId") Long bookId) {
        rateService.rateABook(rate, bookId);
        return Boolean.TRUE;
    }

    @GetMapping("/rate/{bookId}")
    public Byte getRate(@PathVariable("bookId") Long bookId) {
        return userService.getRate(bookId);
    }

    @PostMapping("/book/read")
    public Boolean addReadBook(@RequestParam("id") Long id) {
        userService.addReadBook(id);
        return Boolean.TRUE;
    }

    @PostMapping("/book/favorite")
    public Boolean addFavoriteBook(@RequestParam("id") Long id) {
        userService.addFavoriteBook(id);
        return Boolean.TRUE;
    }

    @DeleteMapping("/book/read")
    public Boolean deleteReadBook(@RequestParam("id") Long id) {
        userService.deleteReadBook(id);
        return Boolean.TRUE;
    }

    @DeleteMapping("/book/favorite")
    public Boolean deleteFavoriteBook(@RequestParam("id") Long id) {
        userService.deleteFavoriteBook(id);
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
