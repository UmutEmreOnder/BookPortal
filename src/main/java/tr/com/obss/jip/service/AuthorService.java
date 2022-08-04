package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.BookDto;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.RespondedRequestDto;
import tr.com.obss.jip.dto.create.CreateNewAuthor;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.model.Author;

import java.util.List;

public interface AuthorService {
    void addNewRequest(CreateNewRequest createNewRequest);
    void createNewAuthor(CreateNewAuthor createNewAuthor);
    Author findAuthorByUsername(String username);

    Author findAuthorById(Long id);

    void save(Author author);

    Iterable<Author> findAll();

    void deleteById(Long id);

    List<RequestDto> getAllRequests();

    List<BookDto> getAllBooks();

    List<RespondedRequestDto> getAllRespondedRequests();
}
