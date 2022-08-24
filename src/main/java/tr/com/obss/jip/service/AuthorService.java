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

    List<RequestDto> getAllRequests(Integer page, Integer pageSize, String field, String order, List<String> genres);

    List<BookDto> getAllBooks(String keyword, Integer page, Integer pageSize, String field, String order, List<String> genres);

    List<RespondedRequestDto> getAllRespondedRequests(Integer page, Integer pageSize, String field, String order, List<String> responses);

    List<AuthorDto> getAllAuthors(String keyword, Integer page, Integer pageSize, String field, String order);

    void updateAuthor(Long id, CreateNewUser createNewAuthor);

    void deleteAuthorById(Long id);


    AuthorDto findByUsername(String username);

    Long getCount();

    Integer getBookCount();

    Integer getRequestCount();

    Integer getRespondCount();
}
