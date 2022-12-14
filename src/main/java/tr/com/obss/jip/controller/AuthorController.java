package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/book")
    public List<BookDto> getAllBooks(@RequestParam("keyword") String keyword, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order, @RequestParam(value = "genre", required = false) List<String> genres) {
        return authorService.getAllBooks(keyword, page, pageSize, field, order, genres);
    }

    @GetMapping("/book/count")
    public Integer getBookCount() {
        return authorService.getBookCount();
    }

    @GetMapping("/current-requests")
    public List<RequestDto> getAllRequests(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order, @RequestParam(value = "genre", required = false) List<String> genres) {
        return authorService.getAllRequests(page, pageSize, field, order, genres);
    }

    @GetMapping("/responded-requests")
    public List<RespondedRequestDto> getAllRespondedRequests(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize, @RequestParam("field") String field, @RequestParam("order") String order, @RequestParam(value = "respond", required = false) List<String> responses) {
        return authorService.getAllRespondedRequests(page, pageSize, field, order, responses);
    }

    @GetMapping("/current-requests/count")
    public Integer getRequestCount() {
        return authorService.getRequestCount();
    }

    @GetMapping("/responded-requests/count")
    public Integer getRespondCount() {
        return authorService.getRespondCount();
    }
}
