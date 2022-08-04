package tr.com.obss.jip.service;

import org.springframework.stereotype.Service;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.model.RespondType;

@Service
public interface RespondedRequestService {
    void create(Request request, Author author, RespondType type);
}
