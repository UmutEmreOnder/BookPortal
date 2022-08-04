package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/create")
    public Boolean addNewRequest(@RequestBody @Valid CreateNewRequest createNewRequest) {
        authorService.addNewRequest(createNewRequest);
        return Boolean.TRUE;
    }

    @GetMapping("/get/books")
    public List<BookDto> getAllBooks() {
        return authorService.getAllBooks();
    }

    @GetMapping("/get/current-requests")
    public List<RequestDto> getAllRequests() {
        return authorService.getAllRequests();
    }
}
