package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.AuthorDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.dto.create.CreateNewUser;
import tr.com.obss.jip.model.Author;

import java.util.List;

public interface AuthorService {
    void addNewRequest(CreateNewRequest createNewRequest);

    void createNewAuthor(CreateNewUser createNewAuthor);

    Author findAuthorByUsername(String username);

    Author findAuthorById(Long id);

    List<RequestDto> getAllRequests();

    List<BookDto> getAllBooks();

    List<RespondedRequestDto> getAllRespondedRequests();

    List<AuthorDto> getAllAuthors();

    void updateAuthor(Long id, CreateNewUser createNewAuthor);

    void updateAuthor(CreateNewUser createNewAuthor);
}
