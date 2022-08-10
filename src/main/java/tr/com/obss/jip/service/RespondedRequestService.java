package tr.com.obss.jip.service;

import org.springframework.stereotype.Service;
import tr.com.obss.jip.model.AddingBookRequest;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.RespondType;

@Service
public interface RespondedRequestService {
    void create(AddingBookRequest addingBookRequest, Author author, RespondType type);
}
