package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.create.CreateNewAuthor;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.model.Author;

public interface AuthorService {
    void addNewRequest(CreateNewRequest createNewRequest);
    void createNewAuthor(CreateNewAuthor createNewAuthor);
    Author findAuthorByUsername(String username);
}
