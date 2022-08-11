package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.AuthorDto;
import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.dto.create.CreateNewUser;

import java.util.List;

public interface AuthorService {
    void addNewRequest(CreateNewRequest createNewRequest);

    void createNewAuthor(CreateNewUser createNewAuthor);

    List<RequestDto> getAllRequests();

    List<BookDto> getAllBooks();

    List<RespondedRequestDto> getAllRespondedRequests();

    List<AuthorDto> getAllAuthors();

    void updateAuthor(Long id, CreateNewUser createNewAuthor);

    void deleteAuthorById(Long id);

    List<BookDto> findByNameContains(String keyword);

    List<AuthorDto> getAllAuthorsContains(String keyword);
}
