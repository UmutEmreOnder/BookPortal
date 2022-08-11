package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.service.AuthorService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Slf4j

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping("/request")
    public Boolean addNewRequest(@RequestBody @Valid CreateNewRequest createNewRequest) {
        authorService.addNewRequest(createNewRequest);
        return Boolean.TRUE;
    }

    @PutMapping("/")
    public Boolean updateAuthor(@RequestParam("id") Long id, @RequestBody @Valid CreateNewUser createNewAuthor) {
        authorService.updateAuthor(id, createNewAuthor);
        return Boolean.TRUE;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return authorService.getAllBooks();
    }

    @GetMapping("/current-requests")
    public List<RequestDto> getAllRequests() {
        return authorService.getAllRequests();
    }

    @GetMapping("/responded-requests")
    public List<RespondedRequestDto> getAllRespondedRequests() {
        return authorService.getAllRespondedRequests();
    }
}
