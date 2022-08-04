package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.create.CreateNewUser;
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

    @PostMapping("/create")
    public Boolean createNewUser(@RequestBody @Valid CreateNewUser createNewUser) {
        userService.createNewUser(createNewUser);
        return true;
    }

    @GetMapping("/book/all")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book/{name}")
    public BookDto findBookByName(@PathVariable("name") String name) {
        return bookService.findByName(name);
    }

    @PostMapping("/book/read/add")
    public Boolean addReadBook(@RequestParam("name") String name) {
        userService.addReadBook(name);
        return Boolean.TRUE;
    }

    @PostMapping("/book/favorite/add")
    public Boolean addFavoriteBook(@RequestParam("name") String name) {
        userService.addFavoriteBook(name);
        return Boolean.TRUE;
    }

    @PostMapping("/book/read/delete")
    public Boolean deleteReadBook(@RequestParam("name") String name) {
        userService.deleteReadBook(name);
        return Boolean.TRUE;
    }

    @PostMapping("/book/favorite/delete")
    public Boolean deleteFavoriteBook(@RequestParam("name") String name) {
        userService.deleteFavoriteBook(name);
        return Boolean.TRUE;
    }
}
