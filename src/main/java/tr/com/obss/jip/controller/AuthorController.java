package tr.com.obss.jip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.service.AuthorService;

import javax.validation.Valid;

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
}
